package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Indonesia implements Idioma {
	Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO: metode dihasilkan secara otomatis
		return "id";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "indonesia";
	}

	@Override
	public String nombre_del_idioma() {
		return "Bahasa Indonesia";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_indonesia.png");
	}

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>bukan folder mods yang valid</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Tidak tahu di mana file JAR "
				+ Statics.nombre_cd.obtener() + " berada</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Mencari PID: " + String.valueOf(pid) + "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") sudah mati!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>JVM tidak ditemukan</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Memperbarui driver ATI/AMD Anda mungkin dapat membantu. Baca panduan ini untuk memperbaikinya: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>Panduan pembaruan driver</a> https://www.amd.com/es/support/download/drivers.html Unduh </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Beberapa versi lama terkadang memiliki masalah dengan beberapa grafis Nouveau pada layar pemuatan awal.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki masalah dengan driver grafis Anda. Jika Anda memiliki GPU atau APU AMD/ATI, perbarui driver grafis AMD Anda. Jika Anda memiliki kartu grafis NVIDIA, pastikan untuk mengatur game dan semua instance javaw.exe agar menggunakan kartu grafis khusus. Baca panduan ini: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Panduan pembaruan driver</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Jendela FML Early Anda mengalami kegagalan. Untuk mengubahnya, buka (.minecraft/config/fml.toml) dan edit earlyWindowProvider menjadi earlyWindowProvider=\"none\". Jika Anda menggunakan Mac M1, pastikan juga menggunakan versi Java ARM, bukan versi Intel x64. Ini juga merupakan masalah umum jika driver Anda sudah usang. Lihat panduan ini jika Anda menggunakan Windows dan menonaktifkan ini tidak berhasil: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Panduan pembaruan driver</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Anda tidak memiliki semua dependensi yang diperlukan:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "Diminta oleh").replace("Expected range", "Rentang yang diharapkan")
				+ "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Laporan " + Statics.nombre_cd.obtener()
				+ " Anda ada di sini <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace()
				+ "'>lihat laporan</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Ini adalah GUI " + Statics.nombre_cd.obtener()
				+ ". Jika game tertutup tanpa masalah, abaikan ini.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>Lihat Laporan</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Lihat laporan lokal di browser Anda.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "Bagikan Laporan";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "Bagikan laporan. Log Anda akan diunggah ke securelogger.net dan laporan Anda akan diunggah ke situs lain.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>File JAR bermasalah terdeteksi (Prioritaskan FATAL, lalu level tinggi dan baris rendah):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> tingkat:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Kemungkinan Fatal:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ModID bermasalah terdeteksi (Prioritaskan FATAL, lalu level rendah dan baris rendah):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Paket bermasalah terdeteksi (Prioritaskan FATAL, lalu level rendah dan baris rendah):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas fatal (FATAL), ini sangat penting. Penyebab umum adalah coremods yang buruk atau dependensi fatal. Anda dapat menggunakan QuickFix untuk mencari mod dengan kelas fatal. Kelas fatal yang hilang terdeteksi:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Konten dalam {} (yang paling penting di atas, hanya 20 pertama):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Konfigurasi SpongeMixin bermasalah terdeteksi: "
				+ "</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki mod dengan paket duplikat. Anda dapat memperbaikinya dengan menghapus paket (folder) dari file JAR. Anda dapat membuka file JAR dengan perangkat lunak arsip seperti WinRAR atau 7z, atau mengubah ekstensi file dari .jar menjadi .zip, menghapus folder tersebut, lalu mengubahnya kembali menjadi file .jar.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Anda memiliki mod duplikat</b> "
				+ linea.replace("from mod files", "dari file mod ");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>MinecraftForge mencurigai mod ini memiliki masalah:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV membutuhkan lithostitched, Anda dapat menginstalnya di sini: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Untuk menggunakan Iris Shaders atau Oculus, Anda memerlukan SODIUM atau versi untuk loader lain (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Masalah dengan ekstensi KubeJS </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Masalah terdeteksi dengan driver NVIDIA pada versi sebelum Windows 11." + "</span><br/><br/>"
				+ "Untuk memastikan Minecraft (dan JVM saat ini) menggunakan kartu grafis NVIDIA khusus, ikuti langkah-langkah berikut:<br/><br/>"
				+ "1. <strong>Identifikasi executable Java:</strong><br/>"
				+ "   - Program ini menggunakan executable Java berikut: " + obtenerRutaJava() + "<br/>"
				+ "   - Jika Anda tidak melihat jalur spesifik, cari 'java.exe' di sistem Anda.<br/><br/>"
				+ "2. <strong>Buka NVIDIA Control Panel:</strong><br/>"
				+ "   - Klik kanan di desktop dan pilih 'NVIDIA Control Panel'.<br/><br/>"
				+ "3. <strong>Atur GPU yang digunakan:</strong><br/>"
				+ "   - Di NVIDIA Control Panel, buka 'Manage 3D settings'.<br/>"
				+ "   - Pilih 'Program Settings'.<br/>"
				+ "   - Klik 'Add' dan pilih executable Java (misalnya 'java.exe').<br/>"
				+ "   - Pastikan disetel ke 'High-performance NVIDIA processor'.<br/><br/>"
				+ "4. <strong>Simpan perubahan:</strong><br/>"
				+ "   - Terapkan perubahan dan tutup NVIDIA Control Panel.<br/><br/>"
				+ "5. <strong>Mulai ulang Minecraft:</strong><br/>"
				+ "   - Mulai ulang Minecraft agar perubahan berlaku.<br/><br/>"
				+ "Jika Anda menggunakan Windows Server 2022 atau Windows 10, langkah-langkah ini berlaku jika driver NVIDIA terbaru sudah terpasang.<br/><br/>"
				+ "Catatan: Jika Anda tidak menemukan NVIDIA Control Panel, pastikan driver NVIDIA terinstal dengan benar.";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Masalah terdeteksi dengan driver NVIDIA pada Windows 11/Server 2025 atau lebih baru."
				+ "</span><br/><br/>"
				+ "Untuk memastikan Minecraft (dan JVM saat ini) menggunakan kartu grafis NVIDIA khusus, ikuti langkah-langkah berikut:<br/><br/>"
				+ "1. <strong>Identifikasi executable Java:</strong><br/>"
				+ "   - Program ini menggunakan executable Java berikut: " + obtenerRutaJava() + "<br/>"
				+ "   - Jika Anda tidak melihat jalur spesifik, cari 'java.exe' di sistem Anda.<br/><br/>"
				+ "2. <strong>Buka aplikasi Pengaturan:</strong><br/>"
				+ "   - Tekan <code>Win + I</code> untuk membuka Pengaturan.<br/>"
				+ "   - Masuk ke <strong>Sistem > Tampilan > Grafis</strong>.<br/><br/>"
				+ "3. <strong>Atur GPU yang digunakan:</strong><br/>"
				+ "   - Di bagian 'Grafis', klik 'Pengaturan grafis default'.<br/>"
				+ "   - Pilih 'Aplikasi desktop' lalu klik 'Browse'.<br/>"
				+ "   - Pilih executable Java (misalnya 'java.exe').<br/>"
				+ "   - Setelah ditambahkan, pilih dan atur ke 'Performa tinggi (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Simpan perubahan:</strong><br/>"
				+ "   - Terapkan perubahan dan tutup Pengaturan.<br/><br/>"
				+ "5. <strong>Mulai ulang Minecraft:</strong><br/>"
				+ "   - Mulai ulang Minecraft agar perubahan berlaku.<br/><br/>"
				+ "Jika Anda menggunakan Windows 11 atau Windows Server 2025+, langkah-langkah ini berlaku jika driver NVIDIA terbaru sudah terpasang.<br/><br/>"
				+ "Catatan: Jika Anda tidak menemukan opsi grafis, pastikan driver NVIDIA terinstal dengan benar.";
	}

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Server atau dunia Anda memiliki tick lebih dari 60 detik. Ini bisa disebabkan oleh mod yang memperlambat server atau perangkat keras yang terlalu lemah.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda tidak memiliki RAM/memori yang cukup. Anda perlu menambah alokasi.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus juga memiliki berbagai masalah, termasuk kegagalan saat menghapus mod. Jika Anda perlu bermain dengan file mrpack, Anda dapat menggunakan launcher lain seperti Prism Launcher (khusus modrinth.com), ATLauncher (khusus modrinth.com), atau Hello Minecraft Launcher (mendukung modrinth.com dan bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Anda menggunakan \"Lewati peluncuran Launcher\" (Aplikasi CurseForge). Terkadang ini menyebabkan masalah yang sulit dideteksi. Hal ini disebabkan oleh opsi \"Lewati peluncuran Launcher\" pada versi lama maupun versi baru aplikasi CurseForge. Nonaktifkan opsi ini dan gunakan \"Mojang Launcher\" di pengaturan CurseForge. Anda dapat menonton video ini (bahasa Inggris) dari Claws of Berk (mulai di 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>di sini</a>.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Peringatan: Anda memiliki kelas yang hilang (peringatan). Biasanya tidak masalah, tetapi tidak selalu. Ini berbeda dengan kelas fatal yang hilang. Coremods yang buruk atau dependensi yang hilang adalah penyebab umum masalah ini. Anda dapat menggunakan QuickFix untuk mencari mod dengan kelas tersebut. Kelas yang hilang terdeteksi:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Tidak ada hasil</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Lokasi Log:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Berikut adalah hasil pemeriksaan Anda. Periksa secara perlahan. Biasanya penyebab yang benar ada di pemeriksaan 1 atau 2. Pemeriksaan lain (kesalahan 3+) dapat digunakan untuk konfirmasi, tetapi biasanya merupakan kesalahan berantai dan dapat diabaikan. Kesalahan terjadi secara berlapis, sehingga memperbaiki penyebab utama akan menyelesaikan kesalahan saat ini, namun bisa muncul kesalahan lain di kemudian hari karena sebelumnya tertutup oleh kesalahan utama.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Silakan gunakan Java 17 untuk versi 1.17–1.20.4, dan Java 21 untuk versi yang lebih baru. Gunakan Java 8 untuk versi lama. [Panduan](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Jika masih bermasalah, kemungkinan ada mod dengan file yang terlalu baru atau terlalu lama.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 dan yang lebih baru tidak berfungsi pada versi Minecraft di bawah 1.20.5 untuk sebagian besar mod loader karena ASM sudah usang.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java sudah usang </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Anda memerlukan modul JPMS " + mod_necesitas
				+ " dari " + submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Tidak dapat memanggil " + metodo + " karena "
				+ objeto + " bernilai null</b>";
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
		return "String Pencarian";
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
		return "Pencarian sedang berlangsung";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "Tidak ada hasil yang ditemukan";
	}

	@Override
	public String errorBusqueda() {
		return "Kesalahan saat pencarian";
	}

	@Override
	public String omitirYCerrar() {
		return "Lewati dan Tutup";
	}

	@Override
	public String guardarYCerrar() {
		return "Simpan dan Tutup";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		return "Tempel log di sini";
	}

	@Override
	public String archivo() {
		return "Berkas";
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
		return "Endpoint Laporan";
	}

	@Override
	public String sitoDeLogging() {
		return "Situs Logging:";
	}

	@Override
	public String apiDeLogging() {
		return "API Logging:";
	}

	@Override
	public String anonimizarRegistros() {
		return "Anonimkan log (Beta)";
	}

	@Override
	public String botonDeCompartirInforme() {
		return "Bagikan laporan dan semua log yang dipilih";
	}

	@Override
	public String arco() {
		return "Dialog ini memungkinkan Anda membagikan log menggunakan API SecureLogger "
				+ "di <a href=\"https://securelogger.net\">securelogger.net</a>. Saat menekan tombol untuk membagikan laporan, "
				+ "laporan Anda akan dikirim ke endpoint yang dipilih (default asbestosstar.egoism.jp) (dapat diubah di bagian bawah). "
				+ "Anda dapat membagikan semua log yang dipilih bersama laporan. Jika Anda tidak ingin mengunggah, jangan gunakan dialog ini! "
				+ "Kami tidak memproses laporan Anda di endpoint resmi (<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb</a>); "
				+ "kami hanya menghapus tautan yang tidak diizinkan. Kode tersedia di sini: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">kode sumber</a>. "
				+ "Ini hanya digunakan untuk menampilkan informasi tentang crash Anda dan tautan ke log. Namun, Anda dapat menggunakan endpoint khusus yang mungkin tidak memiliki metode yang sama. "
				+ "Anda menggunakan situs laporan " + Config.obtenerInstancia().obtenerSitoDeInformes()
				+ " dan situs log " + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "Anda juga dapat membagikan log individual tanpa laporan dengan menekan tombol bagikan di samping nama log; "
				+ "log akan dikirim ke situs log yang dipilih. " + Statics.nombre_cd.obtener()
				+ " memiliki anonimisasi log bawaan yang mencoba menghapus nama pengguna, UUID, "
				+ "token akses, ID sesi, alamat IP, dan data lainnya. Namun, ini tidak sempurna. Pembuat modpack juga dapat menonaktifkannya. "
				+ "Anda dapat mengaktifkan atau menonaktifkannya menggunakan kotak centang di bagian bawah layar ini. Anda mengendalikan data Anda sendiri; Anda memutuskan ke mana data Anda diunggah. "
				+ "Situs log dimiliki oleh pihak ketiga yang kepemilikannya sering disembunyikan demi privasi. Anda bertanggung jawab penuh atas pengelolaan data Anda dan risiko yang terlibat. "
				+ "Dialog Berbagi " + Statics.nombre_cd.obtener()
				+ " hanyalah antarmuka untuk membantu mengelola hal tersebut. "
				+ "Penting untuk memahami GDPR dan ARCO. "
				+ "Jika Anda berada di Eropa, Anda dapat menggunakan <a href=\"https://securelogger.top\">securelogger.top</a> yang dihosting di Jerman oleh Hetzner. "
				+ "Untuk informasi hukum lebih lanjut, lihat tautan berikut: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">GDPR</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">Kebijakan Dasar Perlindungan Data Jepang</a>.";
	}

	@Override
	public String enlaceDelReporte() {
		return "Tautan Laporan:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "Simpan Konfigurasi Berbagi";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "Log terlalu besar untuk situs log ini. Pilih situs lain dan coba lagi.";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "Kesalahan saat memublikasikan log " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "API log tidak tersedia. Silakan ubah API log di pengaturan.";
	}

	@Override
	public String errorSSL() {
		return "Anda mengalami kesalahan SSL. Ini umum terjadi pada versi Java lama, "
				+ "termasuk Java 8 di launcher Minecraft default serta versi dari sun.com dan java.com. "
				+ "Hal ini memengaruhi banyak hal seperti file JAR installer MinecraftForge, fitur berbagi laporan "
				+ Statics.nombre_cd.obtener()
				+ " saat menggunakan endpoint default, beberapa mod yang membutuhkan internet, "
				+ "dan beberapa situs logging. Jika ini terjadi saat mencoba membagikan laporan, "
				+ "cukup lampirkan tangkapan layar dan pilih situs logging yang kompatibel dengan Java 8 lama.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JavaFML tidak kompatibel: Memerlukan versi "
				+ requerido + ", terdeteksi " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Perhatian! JavaFML memerlukan versi Minecraft Forge tertentu</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "File JAR '" + archivoJar
				+ "' memerlukan penyedia bahasa '" + proveedor + "' versi '" + requerido
				+ "' atau lebih tinggi, tetapi hanya ditemukan versi '" + encontrado + "'.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "PERINGATAN! Crash Assistant adalah detektor malware palsu. Ia secara sengaja memblokir peluncuran game, mengabaikan kebebasan Anda untuk tetap bermain dengan mod yang ditandai. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Lihat kode MalwareMod.java</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Lihat kode JarInJarHelper.java</a>. "
				+ "Saat ini hanya mod ini yang ada dalam daftar mereka dan mereka sebenarnya hanya menargetkan situs logging default, yang dapat diubah oleh pengguna, dan itu hanya terjadi jika Anda secara eksplisit memilih menggunakan fitur berbagi log bawaan. CrashAssistant TIDAK melakukan pemeriksaan untuk menentukan situs logging mana yang digunakan atau menjelaskan cara mengubahnya (ada menu dropdown di bagian bawah dialog berbagi), dan terlepas dari situs mana yang Anda pilih, CrashAssistant tetap akan memblokir peluncuran game. Dalam pesannya mereka menyarankan Anda untuk melakukan riset sendiri—LAKUKAN ITU, pelajari kode "
				+ Statics.nombre_cd.obtener()
				+ " dan Crash Assistant, dan pahami apa yang mereka lakukan. JANGAN hanya mengandalkan otoritas.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Mod '" + idMod
				+ "' gagal karena kelas yang diperlukan tidak ditemukan: '" + claseNoEncontrada
				+ "'. Pastikan semua dependensi telah terinstal dengan benar.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Watermedia telah memblokir permainan dengan TLauncher.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Anda menggunakan versi OptiFine untuk versi Minecraft yang tidak sesuai. Anda perlu menggunakan versi OptiFine yang cocok dengan versi Minecraft yang Anda gunakan.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Tidak dapat memuat layanan ModLauncher: </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Kesalahan saat memproses file JSON '"
				+ recurso + "' dari file JAR '" + archivoJar + "'. Terdapat masalah pada registri.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Kesalahan: Mod '" + modId
				+ "' memerlukan versi '" + requerido + "' atau lebih tinggi dari '" + dependencia
				+ "', tetapi ditemukan versi '" + actual + "'." + "</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GPU Anda tidak mendukung versi OpenGL yang diperlukan oleh versi game ini. Perbarui driver Anda atau ganti kartu grafis.</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Tingkatkan memori yang dialokasikan ke game atau kurangi penggunaan mod/plugin.</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "JVM 32-bit terdeteksi: Tidak dapat menggunakan lebih dari 4GB RAM. "
				+ "Instal JVM 64-bit untuk memanfaatkan seluruh memori yang tersedia.</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kesalahan kritis memori PermGen. Tingkatkan ruang memori permanen atau kurangi beban kelas.</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kesalahan kompatibilitas antara Java 8 dan versi modern.</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ tidak didukung — gunakan Java 8 untuk Forge lama.</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 8 diperlukan (versi 52.0). Perbarui atau konfigurasikan dengan benar.</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kesalahan kompatibilitas kritis: Blok tidak didukung dalam versi ini. "
				+ "Pastikan versi mod dan server kompatibel.</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Kesalahan konfigurasi monitor: "
				+ "Tidak dapat mengatur mode tampilan. " + "Periksa:</b>" + "<br>- Konfigurasi multi-monitor"
				+ "<br>- Driver kartu grafis terbaru" + "<br>- Resolusi yang didukung sistem";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Kesalahan pada opsi Java: "
				+ "Opsi garbage collector saling konflik. "
				+ "Pastikan Anda tidak menggabungkan beberapa algoritma GC dalam parameter JVM.</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kesalahan konfigurasi kritis NightConfig/Forge: File konfigurasi rusak atau tidak lengkap. "
				+ "Ini dapat disebabkan oleh file konfigurasi kosong (sering 0 byte) di folder 'config' pada versi lama atau kustom NightConfig. "
				+ "Untuk sebagian besar versi, Night Config Fixes akan menyelesaikan masalah, tetapi jika Anda menggunakan versi yang tidak kompatibel atau kustom, Anda harus menghapus file konfigurasi secara manual. "
				+ "Masalah ini lebih umum pada versi lama MC Forge (yang menyertakan NightConfig) dan mod FabricMC lama yang menyertakan NightConfig, tetapi juga dapat terjadi pada beberapa versi kustom. "
				+ "Informasi lebih lanjut tersedia di <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>.</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kesalahan driver Intel HD Graphics terdeteksi. Solusi:</b>"
				+ "<br>1. Perbarui driver Intel dari <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (versi minimum 15.xx.xx.xx)"
				+ "<br>2. Di Minecraft: Opsi → Video → aktifkan 'Enable VBOs' dan 'VSync'"
				+ "<br>3. Alokasikan 1GB–2GB RAM ke game di launcher"
				+ "<br>4. Nonaktifkan sementara antivirus/firewall saat memperbarui";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "Peringatan: Kelas hilang terdeteksi";
	}

	public String nombre_de_bloque_teselado() {
		return "Kesalahan rendering blok";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "Analisis stack trace";
	}

	public String nombre_de_cursed_consola() {
		return "Konsol CurseForge tidak lengkap";
	}

	public String nombre_de_early_window() {
		return "Kesalahan jendela awal (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "Masalah driver video";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "Konfigurasi MCForge rusak";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "Kegagalan mode tampilan (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "Kesalahan inisialisasi FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "Modul JPMS hilang";
	}

	public String nombre_de_faltar_de_clases() {
		return "Kelas kritis hilang";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "Dependensi ModLauncher hilang";
	}

	public String nombre_de_java_versiones() {
		return "Versi Java tidak kompatibel";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "Kesalahan resource KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "Penyedia bahasa tidak kompatibel";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "Litchhost";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "Deteksi malware palsu";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "Mod mencurigakan terdeteksi";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "Mod duplikat di ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "Konflik modul JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "Sodium diperlukan untuk Iris";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "Kesalahan memproses JSON log";
	}

	public String nombre_de_no_tiene_memoria() {
		return "Memori tidak cukup";
	}

	public String nombre_de_null_pointer() {
		return "Kesalahan NullPointerException";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "Opsi GC Java tidak valid";
	}

	public String nombre_de_optifine_obsoleta() {
		return "OptiFine usang/tidak kompatibel";
	}

	public String nombre_de_60_segundo_trick() {
		return "Tick server kritis (60 detik)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "Layanan ModLauncher gagal";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "Konfigurasi SpongeMixin bermasalah";
	}

	public String nombre_de_theseus() {
		return "Theseus tidak kompatibel";
	}

	public String nombre_de_watermedia_tl() {
		return "TLauncher tidak didukung oleh WATERMeDIA";
	}

	@Override
	public String auditorias_transformer() {
		return "Audit Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Berikut hasil konten Audit Transformer pada Launcher Vanilla. Biasanya kurang akurat dibanding analisis StackTrace, tetapi Launcher Vanilla tidak selalu memiliki konten {}.</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "Ini mencari mod yang dibuat dengan MCreator. Meskipun banyak mod MCreator bagus, terkadang mereka memiliki masalah dan reputasi buruk. Ini membantu mengidentifikasinya. Perlu diingat, bahkan mod populer mungkin hanya memiliki integrasi MCreator, bukan dibuat sepenuhnya dengannya.";
	}

	@Override
	public String escanear() {
		return "Pindai";
	}

	@Override
	public String cargando() {
		return "Memuat";
	}

	@Override
	public String inicioApp() {
		return "Mulai Game/App";
	}

	@Override
	public String ajustesCrashDetector() {
		return "Pengaturan " + Statics.nombre_cd.obtener();
	}

	@Override
	public String confidencialidad() {
		return "Privasi";
	}

	@Override
	public String tooltip() {
		return "Tooltip";
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
		return "Perbarui";
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
		return "Warna tombol (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "Warna kotak teks (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "Warna tautan (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "Warna judul konsol (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "Warna kesalahan (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "Warna peringatan (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "Warna informasi (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "Warna judul (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "Warna teks tautan (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "Hanya buka " + Statics.nombre_cd.obtener() + " saat crash";
	}

	@Override
	public String activar_parche() {
		return "Aktifkan Patch";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "Tidak Ada Solusi Tersedia";
	}

	@Override
	public String error() {
		return "kesalahan";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "Gagal menghapus JAR";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "JAR berhasil dihapus";
	}

	@Override
	public String exito() {
		return "berhasil";
	}

	@Override
	public String eliminar() {
		return "hapus";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "Gagal menghapus paket";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "Paket berhasil dihapus";
	}

	@Override
	public String eliminar_paquete() {
		return "Hapus paket";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "Tidak ditemukan mod dengan paket tersebut";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "Tidak dapat menghapus paket";
	}

	@Override
	public String eliminar_jar() {
		return "Hapus JAR";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "Tidak ditemukan mod duplikat";
	}

	@Override
	public String archivo_no_encontrado() {
		return "Berkas tidak ditemukan";
	}

	@Override
	public String directorio_eliminado() {
		return "Direktori dihapus";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "Gagal menghapus JAR bersarang";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "Berkas internal tidak ditemukan";
	}

	@Override
	public String archivo_eliminado() {
		return "Berkas dihapus";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "Gagal menghapus berkas";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "Berkas eksternal tidak valid";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "Elemen mod dihapus";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "Gagal mengganti JAR eksternal";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "Gagal menghapus elemen mod";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "Gagal menghapus direktori";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "Format tidak valid untuk JAR bersarang";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "JAR bersarang dihapus";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "Gagal membersihkan file sementara";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Pesan Trace Fatal Terakhir (belum diterjemahkan):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Pesan Trace Terakhir (belum diterjemahkan):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "Anda dapat mencari di database WaifuNeoForge untuk menemukan mod. Pilih versi game, loader mod, dan kelas. Gunakan kombinasi yang paling mirip. Pencarian dapat dilakukan sekali per menit.";
	}

	@Override
	public String solucionFaltasClases() {
		return "Anda dapat mencari di database WaifuNeoForge untuk menemukan mod. Pilih versi game, loader mod, dan kelas. Gunakan kombinasi yang paling mirip. Pencarian dapat dilakukan sekali per menit.";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "Kedua launcher memiliki versi Java yang benar tetapi tidak semuanya. Anda dapat menginstal versi Java yang sesuai melalui manajer paket sistem Anda atau menggunakan tombol yang tersedia.";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod dengan animasi hilang: </b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException (elemen tidak ditemukan) – animasi hilang";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "Tidak ditemukan mod untuk dihapus";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "Ganti opsi GC yang konflik dengan -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "Tingkatkan ukuran heap menggunakan opsi -Xmx.";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "Tingkatkan ukuran memori PermGen menggunakan opsi -XX:MaxPermSize.";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "Gunakan JVM 64-bit untuk meningkatkan memori yang tersedia.";
	}

	@Override
	public String optimizarCodigo() {
		return "Optimalkan kode untuk mengurangi penggunaan memori dan meningkatkan kinerja.";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "Gunakan garbage collector yang efisien untuk mengurangi jeda aplikasi.";
	}

	@Override
	public String modulos() {
		return "Modul";
	}

	@Override
	public String paquete() {
		return "Paket";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "Ada ID yang hilang. Penyebab umum adalah mod yang hilang atau data item yang tidak lengkap. Folder data umum meliputi datafiedcontents/ dan kubejs/ atau folder mod lainnya.";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "registri tidak terpetakan dengan benar";
	}

	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Plugin 'AuthMe' gagal dimuat dan telah menghentikan server.</b> ";
	}

	public String nombreProblemaCierreAuthMe() {
		return "Masalah penutupan oleh AuthMe";
	}

	public String solucionCierreAuthMe() {
		return "Aturan 'stopServer' diubah menjadi 'true'.";
	}

	public String solucionConfigurarPluginAuthMe() {
		return "Konfigurasikan plugin AuthMe (plugins/AuthMe/config.yml)";
	}

	public String solucionInstalarVersionDiferenteAuthMe() {
		return "Instal versi lain dari plugin 'AuthMe'";
	}

	public String solucionEliminarPluginAuthMe() {
		return "Hapus plugin 'AuthMe'";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk mundos corruptos por Multiverse.
	 */
	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Dunia '" + nombreMundo
				+ "' tidak dapat dimuat karena mengandung kesalahan dan kemungkinan rusak.</b> ";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaCargaMultiverso() {
		return "Masalah pemuatan Multiverse";
	}

	/**
	 * Mengembalikan solusi untuk memperbaiki dunia.
	 */
	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "Perbaiki dunia '" + nombreMundo + "', misalnya menggunakan Minecraft Region Fixer atau MCEdit.";
	}

	/**
	 * Mengembalikan solusi untuk menghapus folder dunia.
	 */
	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "Hapus folder dunia '" + nombreMundo + "'.";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk configuración inválida de PermissionsEx.
	 */
	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Konfigurasi ekstensi 'PermissionsEx' tidak valid.</b> ";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "Masalah konfigurasi PermissionsEx";
	}

	/**
	 * Mengembalikan solusi untuk mengonfigurasi PermissionsEx.
	 */
	@Override
	public String solucionConfigurarPermissionsEx() {
		return "Konfigurasikan plugin PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	/**
	 * Mengembalikan solusi untuk menghapus plugin PermissionsEx.
	 */
	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "Hapus plugin 'PermissionsEx'";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk plugins con nombre ambiguo.
	 */
	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Ada beberapa file plugin untuk plugin bernama '"
				+ nombrePlugin + "': '" + primerPath + "' dan '" + segundoPath + "'.</b> ";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "Masalah nama plugin ambigu";
	}

	/**
	 * Mengembalikan solusi untuk menghapus plugin tertentu.
	 */
	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "Hapus plugin '" + nombrePlugin + "'";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk pengecualian saat memuat chunk.
	 */
	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Terjadi pengecualian saat dunia memuat chunk. Jika tersedia untuk platform Anda, FeatureRecycler mungkin dapat memperbaiki masalah ini. https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaCargaChunk() {
		return "Pengecualian saat memuat chunk";
	}

	/**
	 * Mengembalikan solusi untuk menghapus chunk yang bermasalah.
	 */
	@Override
	public String solucionEliminarChunk() {
		return "Hapus chunk bermasalah dari dunia, misalnya dengan MCEdit atau dengan menghapus file region.";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk excepciones al jalankanr comandos de
	 * plugins.
	 */
	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' tidak dapat menjalankan perintah '/" + comando + "'.</b> ";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "Pengecualian saat menjalankan perintah plugin";
	}

	/**
	 * Mengembalikan solusi untuk memasang versi lain dari plugin.
	 */
	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "Instal versi lain dari plugin '" + nombrePlugin + "'";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk dependensi individual.
	 */
	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' memerlukan ekstensi '" + dependencia + "'.</b> ";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk beberapa dependensi.
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
				+ "' kekurangan plugin wajib berikut " + deps.toString() + ".</b> ";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "Dependensi plugin yang hilang";
	}

	/**
	 * Mengembalikan solusi untuk memasang plugin tertentu.
	 */
	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "Instal plugin '" + nombrePlugin + "'";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk versi API yang tidak kompatibel.
	 */
	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' memerlukan versi API '" + versionAPI + "' yang tidak kompatibel dengan server saat ini.</b> ";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "Versi API tidak kompatibel";
	}

	/**
	 * Mengembalikan solusi untuk memasang versi server tertentu.
	 */
	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "Instal versi '" + version + "' dari perangkat lunak server Anda.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Dunia '" + nombreMundo
				+ "' merupakan duplikat dari dunia lain dan tidak dapat dimuat.</b> ";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaMundoDuplicado() {
		return "Dunia duplikat";
	}

	/**
	 * Mengembalikan solusi untuk menghapus file 'uid.dat' dari dunia.
	 */
	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "Hapus file 'uid.dat' dari dunia '" + nombreMundo + "'";
	}

	/**
	 * Mengembalikan solusi untuk menghapus seluruh folder dunia.
	 */
	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "Hapus folder dunia '" + nombreMundo + "'";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk entitas atau entitas blok problemáticas,
	 * detallando los pasos de recuperación según la plataforma.
	 */
	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>Entitas atau Entitas Blok '</span>" + nombre
				+ "<span style='color:#" + color + "'>' dengan tipe '</span>" + tipo + "<span style='color:#" + color
				+ "'>' di lokasi </span>" + coords + "<span style='color:#" + color
				+ "'> menyebabkan kesalahan ticking.</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "Petunjuk pemulihan:<br>"
				+ "1. **MCForge**: Buka '[nombre_del_mundo]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: Buka 'config/neoforge-server.toml'.<br>"
				+ "   *(Catatan: Dalam permainan lokal/Singleplayer, dunia berada di folder 'saves')*.<br>"
				+ "3. Ubah **removeErroringBlockEntities** dan **removeErroringEntities** menjadi **true**.<br><br>"
				+ "Opsi lain:<br>"
				+ "- **MCEdit**: Gunakan untuk menghapus entitas secara manual pada koordinat yang ditunjukkan.<br>"
				+ "- **Neruina (Mod)**: Dapat mencegah crash, tetapi tidak selalu berfungsi dan dapat mempersulit debug saat terpasang."
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "Entitas blok bermasalah";
	}

	/**
	 * Mengembalikan solusi untuk menghapus entitas blok.
	 */
	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "Hapus entitas '" + nombre + "' di lokasi " + coords
				+ " menggunakan MCEdit atau dengan mengedit dunia secara langsung.";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk mod duplikat di Fabric.
	 */
	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' memiliki beberapa versi yang terpasang.</b> ";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Mod duplikat di Fabric";
	}

	/**
	 * Mengembalikan solusi untuk menghapus file duplikat.
	 */
	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "Hapus file mod duplikat: " + rutaMod;
	}

	/**
	 * Mengembalikan pesan kesalahan untuk mod yang tidak kompatibel.
	 */
	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + primerMod + "' dan '" + segundoMod
				+ "' tidak kompatibel satu sama lain.</b> ";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Mod tidak kompatibel di Fabric";
	}

	/**
	 * Mengembalikan solusi untuk menghapus mod pertama yang tidak kompatibel.
	 */
	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "Hapus mod '" + nombreMod + "'";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk mod dengan masalah fatal.
	 */
	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' memiliki kesalahan fatal dan tidak dapat dijalankan.</b> ";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaModFatal() {
		return "Mod dengan kesalahan fatal";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk dependensi mod yang hilang (jamak).
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
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod berikut diperlukan tetapi belum terpasang: "
				+ deps.toString() + ".</b>";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk dependensi mod yang hilang (tunggal).
	 */
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

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaDependenciaMod() {
		return "Dependensi mod yang hilang";
	}

	/**
	 * Mengembalikan solusi untuk memasang mod tertentu.
	 */
	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "Instal mod '" + nombreMod + "'";
	}

	/**
	 * Mengembalikan solusi untuk memasang mod dengan versi tertentu.
	 */
	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "Instal mod '" + nombreMod + "' dengan versi " + version;
	}

	/**
	 * Mengembalikan pesan kesalahan untuk plugins que no soportan ticking regional
	 * (tunggal).
	 */
	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' tidak kompatibel dengan ticking regional Folia.</b> ";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk múltiples plugins que no soportan ticking
	 * regional (jamak).
	 */
	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugin berikut tidak kompatibel dengan ticking regional Folia: ");

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
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaPluginTickingRegional() {
		return "Plugin tidak kompatibel dengan ticking regional";
	}

	/**
	 * Mengembalikan solusi untuk memasang perangkat lunak tanpa ticking regional.
	 */
	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "Instal perangkat lunak tanpa ticking regional, seperti " + software;
	}

	/**
	 * Mengembalikan pesan kesalahan untuk mod yang hilang dalam datapack (tunggal).
	 */
	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod + "' hilang dari datapack.</b>";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk beberapa mod yang hilang dalam datapack
	 * (jamak).
	 */
	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mod berikut hilang dari datapack: ");

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

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaModFaltanteEnDatapack() {
		return "Mod hilang di datapack";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk mod yang menghasilkan pengecualian
	 * (tunggal).
	 */
	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' telah menghasilkan kesalahan.</b>";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk beberapa mod yang menghasilkan
	 * pengecualian (jamak).
	 */
	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mod berikut telah menghasilkan kesalahan: ");

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

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaModExcepcion() {
		return "Mod Forge dengan pengecualian";
	}

	/**
	 * Mengembalikan solusi untuk memasang versi lain dari mod.
	 */
	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "Instal versi lain dari mod '" + nombreMod + "'";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk mod yang tidak kompatibel dengan versi
	 * Minecraft (singular).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' tidak kompatibel dengan versi Minecraft " + versionMC + ".</b>";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk beberapa mod yang tidak kompatibel dengan
	 * Minecraft (jamak).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mod berikut tidak kompatibel dengan versi Minecraft: ");

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
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "Mod tidak kompatibel dengan Minecraft";
	}

	/**
	 * Mengembalikan solusi untuk memasang versi Forge yang berbeda.
	 */
	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "Instal versi Forge yang kompatibel dengan Minecraft " + versionMC;
	}

	/**
	 * Mengembalikan pesan kesalahan untuk mod yang hilang (tunggal).
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' hilang dan diperlukan untuk memuat dunia atau plugin.</b>";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "Mod hilang";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk mod yang hilang di dunia (tunggal).
	 */
	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Dunia disimpan dengan mod '" + nombreMod
				+ "' yang tampaknya sekarang tidak ada.</b>";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk beberapa mod yang hilang di dunia
	 * (jamak).
	 */
	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Dunia disimpan dengan mod berikut yang tampaknya sekarang tidak ada: ");

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

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaWorldModFaltante() {
		return "Mod hilang di dunia";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk perbedaan versi mod dalam sebuah dunia
	 * (tunggal).
	 */
	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Dunia disimpan dengan mod '" + nombreMod
				+ "' versi " + versionEsperada + ", dan sekarang versinya adalah " + versionActual + ".</b>";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk perbedaan versi mod pada beberapa mod
	 * (jamak).
	 */
	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mod berikut memiliki perbedaan versi dalam dunia yang disimpan: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" dan ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (Disimpan: ").append(versionesEsperadas.get(i)).append(", Saat ini: ")
					.append(versionesActuales.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaVersionModMundo() {
		return "Versi mod dalam dunia yang disimpan";
	}

	/**
	 * Mengembalikan pesan kesalahan saat mencoba memuat dunia dari versi más
	 * reciente.
	 */
	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda mencoba memuat dunia yang dibuat dengan versi Minecraft yang lebih baru.</b>";
	}

	/**
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaVersionDowngrade() {
		return "Percobaan memuat dunia dari versi lebih lama";
	}

	/**
	 * Mengembalikan solusi untuk memasang versi Minecraft yang lebih baru.
	 */
	@Override
	public String solucionVersionDowngrade() {
		return "Instal versi Minecraft yang lebih baru.";
	}

	/**
	 * Mengembalikan solusi untuk membuat dunia baru.
	 */
	@Override
	public String solucionGenerarNuevoMundo() {
		return "Buat dunia baru.";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk plugin dengan dependensi yang hilang
	 * (tunggal).
	 */
	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin + "' memerlukan plugin '"
				+ dependencia + "' sebagai dependensi.</b>";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk beberapa plugin dengan dependensi
	 * faltantes (plural).
	 */
	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugin berikut memerlukan dependensi yang belum terpasang: ");

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
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaDependenciaPluginFaltante() {
		return "Plugin dengan dependensi yang hilang";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk plugin yang tidak kompatibel (tunggal).
	 */
	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Ekstensi '" + nombrePlugin
				+ "' tidak kompatibel dengan versi server saat ini.</b>";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk múltiples plugins incompatibles (plural).
	 */
	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Ekstensi berikut tidak kompatibel dengan versi server saat ini: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" dan ");
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
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaPluginIncompatible() {
		return "Plugin tidak kompatibel dengan PocketMine-MP";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk plugin dengan kesalahan saat dijalankan
	 * (tunggal).
	 */
	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' telah menghasilkan kesalahan saat dijalankan.</b>";
	}

	/**
	 * Mengembalikan pesan kesalahan untuk beberapa plugin dengan kesalahan saat
	 * ejecución (plural).
	 */
	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugin berikut telah menghasilkan kesalahan saat dijalankan: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" dan ");
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
	 * Mengembalikan nama masalah untuk ditampilkan di antarmuka.
	 */
	@Override
	public String nombreProblemaPluginEjecucion() {
		return "Plugin dengan kesalahan saat dijalankan";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "LegacyRandomSource Multi-Thread";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki masalah dengan beberapa thread yang mengakses kelas LegacyRandomSource. Anda bisa mendapatkan informasi lebih lanjut dengan mod Unsafe World Random Access Detector atau mod C2ME.</b> ";
	}

	@Override
	public String desdeUltimoExito() {
		return "Sejak keberhasilan terakhir";
	}

	@Override
	public String noHayCambios() {
		return "Tidak ada perubahan";
	}

	@Override
	public String desdeUltimoIntento() {
		return "Sejak percobaan terakhir";
	}

	@Override
	public String fallo() {
		return "Gagal";
	}

	@Override
	public String diferentesDeLasMods() {
		return "Berbeda dari mod-mod";
	}

	@Override
	public String historialDeMods() {
		return "RiwayatMod";
	}

	@Override
	public String archivo0() {
		return "File0";
	}

	@Override
	public String archivo1() {
		// TODO: metode dihasilkan secara otomatis
		return "File1";
	}

	@Override
	public String comparar() {
		// TODO: metode dihasilkan secara otomatis
		return "Bandingkan";
	}

	@Override
	public String seleccionarDosArchivos() {
		// TODO: metode dihasilkan secara otomatis
		return "Pilih Dua File";
	}

	@Override
	public String archivoExito() {
		// TODO: metode dihasilkan secara otomatis
		return "File Berhasil";
	}

	@Override
	public String archivoFalla() {
		// TODO: metode dihasilkan secara otomatis
		return "File Gagal";
	}

	@Override
	public String errorComparandoArchivos() {
		// TODO: metode dihasilkan secara otomatis
		return "Kesalahan Saat Membandingkan File";
	}

	@Override
	public String comparando() {
		// TODO: metode dihasilkan secara otomatis
		return "Membandingkan";
	}

	@Override
	public String con() {
		// TODO: metode dihasilkan secara otomatis
		return "Dengan";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<p><b>Panel Perbandingan Riwayat Mod</b></p>"
				+ "<p>Panel ini memungkinkan Anda membandingkan dua daftar mod (modul) dari sesi eksekusi yang berbeda. "
				+ "Pilih satu file dari kolom kiri dan satu lagi dari kolom kanan untuk melihat perubahan yang dilakukan di antara kedua versi tersebut.</p>"

				+ "<h3>Cara menggunakannya:</h3>" + "<ol>"
				+ "<li><b>Pemilihan file:</b> Klik tombol radio di samping nama file. "
				+ "File yang berakhiran <span style='color: #4CAF50; font-weight: bold;'>.exito</span> menunjukkan sesi yang berhasil, "
				+ "sedangkan yang berakhiran <span style='color: #F44336; font-weight: bold;'>.falla</span> menunjukkan kegagalan.</li>"

				+ "<li><b>Perbandingan otomatis:</b> Saat menekan tombol 'Compare', sistem akan menganalisis dua daftar yang dipilih "
				+ "dan menampilkan mod yang ditambahkan (+) atau dihapus (-) ke setiap arah.</li>"

				+ "<li><b>Tampilan hasil:</b> Hasil ditampilkan dalam format HTML dengan warna berbeda: " + "<ul>"
				+ "<li><span style='color: green;'>+ Mod ditambahkan</span></li>"
				+ "<li><span style='color: red;'>- Mod dihapus</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>Fitur utama:</h3>" + "<ul>"
				+ "<li>Mendukung pemilihan kombinasi file apa pun (berhasil/gagal).</li>"
				+ "<li>Menampilkan perbedaan dua arah untuk mengidentifikasi perubahan dengan tepat.</li>"
				+ "<li>Menyertakan scroll untuk menavigasi daftar mod yang panjang.</li>"
				+ "<li>Terintegrasi dengan gambar penjelas untuk meningkatkan pemahaman visual.</li>" + "</ul>"

				+ "<p>Dikembangkan dengan <3️ untuk membantu Anda melacak perubahan pada konfigurasi Anda.</p>"
				+ "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mungkin Anda memiliki masalah yang terkait dengan IPv6. " + "Ada dua solusi: "
				+ "1) Tambahkan argumen JVM <code>-Djava.net.preferIPv4Stack=true</code> di launcher Anda, atau "
				+ "2) Gunakan tombol 'QuickFix' di " + Statics.nombre_cd.obtener()
				+ " untuk menerapkan patch yang mengaktifkan konfigurasi ini secara otomatis." + "</b>";
	}

	@Override
	public String parcheIPv4() {
		// TODO: metode dihasilkan secara otomatis
		return "Patch IPV4/6";
	}

	@Override
	public String carpetaHMCL() {
		// TODO: metode dihasilkan secara otomatis
		return "Folder HMCL (Hanya untuk HelloMineCraftLauncher)";
	}

	@Override
	public String descripcionCurseforge() {
		return "Catatan: Log tidak dihasilkan jika opsi \"Skip Launcher\" diaktifkan di Settings > Minecraft";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/Turunan: Klik kanan pada instance dan pilih \"Edit Instance\". Di jendela yang terbuka, cari bagian \"Minecraft Logs\" atau serupa.<br>"
				+ "Log ini berisi output standar (STDOUT) yang fundamental untuk mendiagnosis kesalahan.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): Anda harus memilih folder tempat HMCL diinstal dan memilih folder \".hcml\". Log HMCL disimpan di lokasi ini dan berisi informasi penting tentang kesalahan.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: Launcher memiliki tab pengembangan yang menampilkan log terperinci. Anda dapat menemukan tab ini di menu opsi launcher.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: Ada dialog popup dengan log. Ini memiliki tombol untuk menyalin dan mengunggah log. Log dihasilkan secara otomatis saat memulai game dan berisi informasi kritis untuk diagnosis.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: Klik kanan pada instance dan pilih \"Konfigurasi\". Kemudian pergi ke bagian Log untuk melihat informasi penting yang berisi output standar (STDOUT).";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Tautan dengan Markdown: Tempel di sini tautan apa pun yang berisi log dalam format Markdown. Sistem akan mencoba mengekstrak tautan ke log (latest.log, launcher_log.txt, debug.log, dll.) dan menganalisisnya secara otomatis.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "Log Launcher Tidak Ditemukan";
	}

	@Override
	public String imagenNoEncontrada() {
		return "Gambar tidak ditemukan";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "GENERIC: Pilih jenis launcher yang Anda gunakan. Log launcher (launcher_log.txt, stdout, dll.) berisi informasi krusial tentang kesalahan yang tidak muncul di latest.log. "
				+ Statics.nombre_cd.obtener()
				+ " tidak dapat membaca log Launcher Anda, mungkin itu tidak memiliki file log dan Anda harus menempel log secara manual.<br>"
				+ "Untuk informasi lebih lanjut, lihat <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">masalah ini</a>. Log ini berisi output standar (STDOUT), diperlukan untuk mendiagnosis banyak jenis kesalahan.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas yang hilang dari Create. Create berubah banyak: banyak kelas dihapus. Terutama sejak Create 6 (Februari 2025), addon untuk versi Create lama tidak berfungsi. QuickFix tidak memiliki solusi untuk masalah ini. Anda perlu memperbarui addon Create, menghapus yang usang, atau menggunakan versi Create yang benar untuk addon yang ingin Anda gunakan.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas yang hilang dari EpicFight. EpicFight berubah banyak: banyak kelas dihapus. QuickFix tidak memiliki solusi untuk masalah ini. Anda perlu memperbarui addon EpicFight, menghapus yang usang, atau menggunakan versi EpicFight yang benar untuk addon yang ingin Anda gunakan.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda menggunakan OpenJ9, yang tidak kompatibel dengan Aplikasi Ini. Banyak aplikasi, termasuk ini, tidak mendukung OpenJ9 karena perbedaan dalam implementasi JVM. QuickFix tidak dapat mengatasi masalah ini secara otomatis. Anda perlu menginstal JDK yang kompatibel seperti Oracle JDK, OpenJDK Hotspot, atau alternatif lain yang direkomendasikan.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Versi aplikasi ini memerlukan Java 11 (JDK 11) untuk berfungsi dengan benar. Anda menggunakan versi Java yang lebih lama yang tidak kompatibel. QuickFix tidak dapat memperbarui Java Anda secara otomatis. Anda perlu mengunduh dan menginstal JDK 11 atau versi yang lebih tinggi dan kompatibel dari tautan yang diberikan dalam solusi.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda telah mengalokasikan terlalu banyak memori, yang menyebabkan sistem tidak memiliki sumber daya yang cukup untuk berfungsi dengan baik. Ini biasanya terjadi ketika Anda menentukan jumlah RAM lebih besar dari yang tersedia di sistem Anda atau ketika menggunakan JVM 32-bit yang tidak dapat menangani jumlah memori yang besar.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Untuk mengatasi masalah ini, Anda harus mengurangi jumlah memori yang dialokasikan untuk aplikasi. Jumlah maksimum yang direkomendasikan tergantung pada sistem Anda, tetapi umumnya tidak boleh melebihi 70-80% dari total RAM Anda. Jika Anda menggunakan JVM 32-bit, batas maksimum adalah sekitar 2-3 GB, terlepas dari jumlah RAM fisik yang Anda miliki.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Untuk mengurangi memori heap yang dialokasikan untuk aplikasi, buka pengaturan launcher Anda dan cari opsi RAM memori. Kurangi nilai maksimum (Xmx) ke jumlah yang lebih sesuai. Misalnya, jika Anda memiliki 8 GB RAM, alokasikan 3-4 GB untuk aplikasi. Jika Anda memiliki 16 GB RAM, Anda dapat mengalokasikan 6-8 GB. Ingat untuk meninggalkan cukup memori untuk sistem operasi dan program lain.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>File penting Forge hilang. File '" + archivo
				+ "' tidak ditemukan dalam instalasi Anda. Ini biasanya terjadi ketika instalasi Forge terputus atau file penting dihapus. QuickFix tidak dapat memulihkan file ini secara otomatis. Anda perlu menginstal ulang Forge dengan benar dari installer resmi.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge tidak dapat menemukan versi Minecraft yang diperlukan. Versi " + version
				+ " diperlukan tetapi tidak ditemukan di file '" + archivo
				+ "'. Ini terjadi ketika ada ketidaksesuaian antara versi Minecraft dan versi Forge yang Anda gunakan. Pastikan Anda mengunduh versi Forge yang benar yang cocok dengan versi Minecraft Anda.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Target 'fmlclient' yang diperlukan untuk memulai Forge tidak dapat ditemukan. Ini menunjukkan bahwa instalasi Forge tidak lengkap atau rusak. Kemungkinan file penting Forge tidak diinstal dengan benar. Anda perlu menginstal ulang Forge menggunakan installer resmi.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kelas utama Minecraft tidak dapat ditemukan di class loader. Ini biasanya menunjukkan bahwa instalasi Forge tidak lengkap atau ada konflik dengan mod lain. Dimungkinkan file Minecraft rusak selama instalasi Forge. Anda perlu menginstal ulang Forge dengan benar.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Instalasi Forge tidak lengkap. Ini dapat disebabkan oleh instalasi yang terputus, file dihapus, atau ketidaksesuaian dengan versi Minecraft Anda. Forge memerlukan file spesifik untuk berfungsi dengan benar, dan beberapa di antaranya hilang dari instalasi saat ini Anda.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Instalasi Forge tidak lengkap";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Untuk mengatasi masalah ini, Anda perlu menginstal ulang Forge dengan benar. Pastikan Anda mengunduh versi yang tepat untuk versi Minecraft Anda dan ikuti proses instalasi lengkap tanpa mengganggu.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "Unduh Forge secara resmi";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "Cara menginstal ulang Forge dengan benar";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>Instruksi menginstal ulang Forge:</h3>" + "<ol>"
				+ "<li>Unduh installer yang benar dari Forge dari situs resmi (versi yang direkomendasikan untuk versi Minecraft Anda)</li>"
				+ "<li>Tutup sepenuhnya launcher Minecraft Anda</li>"
				+ "<li>Jalankan installer Forge sebagai administrator</li>"
				+ "<li>Pilih opsi 'Installer' (bukan 'Installer (run client)')</li>"
				+ "<li>Pilih folder profil Minecraft Anda di launcher</li>"
				+ "<li>Tekan 'OK' dan tunggu instalasi selesai</li>"
				+ "<li>Restart launcher Anda dan verifikasi Forge muncul dalam daftar profil</li>" + "</ol>"
				+ "<p><b>Catatan penting:</b> Jika Anda menggunakan launcher khusus, pastikan Anda memilih folder profil yang benar.</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Instruksi menginstal ulang Forge";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kesalahan tautan yang tidak puas: Tidak dapat memuat library " + nombreBiblioteca
				+ ". Solusi yang mungkin:<br/><br/>"
				+ "a) Tambahkan direktori yang berisi library bersama ke -Djava.library.path atau -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Tambahkan file JAR yang berisi library bersama ke classpath.<br/><br/>"
				+ "Kesalahan ini terjadi ketika Minecraft tidak dapat menemukan file penting untuk berfungsi. "
				+ "Ini biasanya karena instalasi Minecraft yang tidak lengkap atau masalah dengan izin sistem.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "Kesalahan tautan yang tidak puas";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Tidak dapat memuat library. Solusi yang mungkin:<br/><br/>"
				+ "a) Tambahkan direktori yang berisi library bersama ke -Djava.library.path atau -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Tambahkan file JAR yang berisi library bersama ke classpath.<br/><br/>"
				+ "Solusi teknis ini untuk pengguna lanjutan. Sebagian besar pengguna harus mencoba "
				+ "menginstal ulang Minecraft sebelum memodifikasi parameter ini.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Kolisi ID: ID <strong>" + id
				+ "</strong> sudah ditempati oleh <strong>" + modOrigen + "</strong> saat mencoba menambahkan <strong>"
				+ modDestino
				+ "</strong>. Ini terjadi ketika dua mod mencoba menggunakan ID yang sama untuk elemen berbeda.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Rentang maksimum ID yang diizinkan telah terlampaui. Ini terjadi ketika mod mencoba mendaftarkan blok atau item dengan ID di luar rentang yang diizinkan oleh versi Minecraft Anda.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "Konflik ID";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Untuk mengatasi ini di Minecraft 1.12.2, instal <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. Untuk 1.7.10, gunakan <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Gunakan alat seperti <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> atau <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> untuk mengatasi kolisi ID.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "Instal JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "Instal EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "Gunakan IdFix Minus atau IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "Gunakan Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "Lihat dokumentasi Jepang";
	}

	@Override
	public String escanearDeMCreator() {
		// TODO: metode dihasilkan secara otomatis
		return "Pindai MCreator";
	}

	/**
	 * Mengambil judul jendela pohon mod.
	 * 
	 * @return Judul jendela
	 */
	@Override
	public String tituloArbolDeMods() {
		return "Pohon Modul dan Kelas";
	}

	/**
	 * Mengambil teks untuk label jenis pencarian.
	 * 
	 * @return Teks label
	 */
	@Override
	public String tipoBusqueda() {
		return "Jenis";
	}

	/**
	 * Mengambil teks untuk filter "Semua".
	 * 
	 * @return Teks filter
	 */
	@Override
	public String filtroTodos() {
		return "Semua";
	}

	/**
	 * Mengambil teks untuk filter "Paket".
	 * 
	 * @return Teks filter
	 */
	@Override
	public String filtroPaquetes() {
		return "Paket";
	}

	/**
	 * Mengambil teks untuk filter "Kelas".
	 * 
	 * @return Teks filter
	 */
	@Override
	public String filtroClases() {
		return "Kelas";
	}

	/**
	 * Mengambil teks untuk filter "Metode".
	 * 
	 * @return Teks filter
	 */
	@Override
	public String filtroMetodos() {
		return "Metode";
	}

	/**
	 * Mengambil teks untuk filter "Bidang".
	 * 
	 * @return Teks filter
	 */
	@Override
	public String filtroCampos() {
		return "Bidang";
	}

	/**
	 * Mengambil teks untuk filter "Referensi Bidang".
	 * 
	 * @return Teks filter
	 */
	@Override
	public String filtroReferenciasCampo() {
		return "Referensi Bidang";
	}

	/**
	 * Mengambil teks untuk filter "Referensi Metode".
	 * 
	 * @return Teks filter
	 */
	@Override
	public String filtroReferenciasMetodo() {
		return "Referensi Metode";
	}

	/**
	 * Mengambil teks untuk filter "Referensi Kelas".
	 * 
	 * @return Teks filter
	 */
	@Override
	public String filtroReferenciasClase() {
		return "Referensi Kelas";
	}

	/**
	 * Mengambil teks untuk tooltip bidang pencarian.
	 * 
	 * @return Teks tooltip
	 */
	@Override
	public String tipBuscar() {
		return "Ketik di sini untuk mencari di pohon mod";
	}

	/**
	 * Mengambil teks untuk tombol pencarian.
	 * 
	 * @return Teks tombol
	 */
	@Override
	public String botonBuscar() {
		return "Cari";
	}

	/**
	 * Mengambil teks untuk tombol setel ulang pohon.
	 * 
	 * @return Teks tombol
	 */
	@Override
	public String botonResetearArbol() {
		return "Atur Ulang Pohon";
	}

	/**
	 * Mengambil teks untuk menunjukkan mod yang dimuat.
	 * 
	 * @return Teks deskriptif
	 */
	@Override
	public String modsCargados() {
		return "Mod Dimuat";
	}

	/**
	 * Mengambil teks untuk kategori kelas.
	 * 
	 * @return Teks kategori
	 */
	@Override
	public String clases() {
		return "Kelas";
	}

	/**
	 * Mengambil teks untuk kategori metode.
	 * 
	 * @return Teks kategori
	 */
	@Override
	public String metodos() {
		return "Metode";
	}

	/**
	 * Mengambil teks untuk kategori bidang.
	 * 
	 * @return Teks kategori
	 */
	@Override
	public String campos() {
		return "Bidang";
	}

	/**
	 * Mengambil teks untuk kategori referensi.
	 * 
	 * @return Teks kategori
	 */
	@Override
	public String referencias() {
		return "Referensi";
	}

	/**
	 * Mengambil teks untuk hasil pencarian.
	 * 
	 * @return Teks hasil
	 */
	@Override
	public String resultadosBusqueda() {
		return "Hasil Pencarian";
	}

	/**
	 * Mengambil teks untuk opsi mencari referensi.
	 * 
	 * @return Teks opsi
	 */
	@Override
	public String buscarReferencias() {
		return "Cari Referensi";
	}

	/**
	 * Mengambil teks untuk referensi mod.
	 * 
	 * @return Teks deskriptif
	 */
	@Override
	public String referenciasMod() {
		return "Referensi Mod";
	}

	/**
	 * Mengambil teks untuk referensi kelas.
	 * 
	 * @return Teks deskriptif
	 */
	@Override
	public String referenciasClase() {
		return "Referensi Kelas";
	}

	/**
	 * Mengambil teks untuk referensi metode.
	 * 
	 * @return Teks deskriptif
	 */
	@Override
	public String referenciasMetodo() {
		return "Referensi Metode";
	}

	/**
	 * Mengambil teks untuk referensi bidang.
	 * 
	 * @return Teks deskriptif
	 */
	@Override
	public String referenciasCampo() {
		return "Referensi Bidang";
	}

	/**
	 * Mengambil teks saat tidak ada referensi yang ditemukan.
	 * 
	 * @return Pesan kesalahan
	 */
	@Override
	public String noSeEncontraronReferencias() {
		return "Tidak ditemukan referensi";
	}

	/**
	 * Mengambil teks untuk detail mod.
	 * 
	 * @return Judul detail
	 */
	@Override
	public String detalleMod() {
		return "Detail Mod:";
	}

	/**
	 * Mengambil teks untuk lokasi.
	 * 
	 * @return Label lokasi
	 */
	@Override
	public String ubicacion() {
		return "Lokasi";
	}

	/**
	 * Mengambil teks untuk nama-nama.
	 * 
	 * @return Label nama
	 */
	@Override
	public String nombres() {
		return "Nama";
	}

	/**
	 * Mengambil teks untuk modul.
	 * 
	 * @return Label modul
	 */
	@Override
	public String modulo() {
		return "Modul";
	}

	/**
	 * Mengambil teks untuk detail kelas.
	 * 
	 * @return Judul detail
	 */
	@Override
	public String detalleClase() {
		return "Detail Kelas:";
	}

	/**
	 * Mengambil teks untuk detail metode.
	 * 
	 * @return Judul detail
	 */
	@Override
	public String detalleMetodo() {
		return "Detail Metode:";
	}

	/**
	 * Mengambil teks untuk detail bidang.
	 * 
	 * @return Judul detail
	 */
	@Override
	public String detalleCampo() {
		return "Detail Bidang:";
	}

	/**
	 * Mengambil teks untuk kelas.
	 * 
	 * @return Label kelas
	 */
	@Override
	public String clase() {
		return "Kelas";
	}

	/**
	 * Mengambil teks untuk tipe.
	 * 
	 * @return Label tipe
	 */
	@Override
	public String tipo() {
		return "Jenis";
	}

	/**
	 * Mengambil teks untuk referensi ke metode.
	 * 
	 * @return Label referensi
	 */
	@Override
	public String referenciasAMetodos() {
		return "Referensi ke Metode:";
	}

	/**
	 * Mengambil teks untuk referensi ke bidang.
	 * 
	 * @return Label referensi
	 */
	@Override
	public String referenciasACampos() {
		return "Referensi ke Bidang:";
	}

	/**
	 * Mengambil teks untuk tombol pohon mod.
	 * 
	 * @return Teks tombol
	 */
	@Override
	public String arbolDeMods() {
		return "Pohon Mod";
	}

	/**
	 * Mengambil teks untuk metode.
	 * 
	 * @return Kata "Metode"
	 */
	@Override
	public String metodo() {
		return "Metode";
	}

	/**
	 * Mengambil teks untuk bidang.
	 * 
	 * @return Kata "Bidang"
	 */
	@Override
	public String campo() {
		return "Bidang";
	}

	@Override
	public String descompilar() {
		// TODO: metode dihasilkan secara otomatis
		return "Dekompilasi";
	}

	@Override
	public String exportar() {
		// TODO: metode dihasilkan secara otomatis
		return "Ekspor";
	}

	@Override
	public String importar() {
		// TODO: metode dihasilkan secara otomatis
		return "Impor";
	}

	@Override
	public String errorImportar() {
		// TODO: metode dihasilkan secara otomatis
		return "Kesalahan Impor";
	}

	@Override
	public String estructuraImportada() {
		// TODO: metode dihasilkan secara otomatis
		return "Struktur Diimpor";
	}

	@Override
	public String estructuraExportada() {
		// TODO: metode dihasilkan secara otomatis
		return "Struktur Diekspor";
	}

	@Override
	public String errorExportar() {
		// TODO: metode dihasilkan secara otomatis
		return "Kesalahan Ekspor";
	}

	@Override
	public String exportando() {
		// TODO: metode dihasilkan secara otomatis
		return "Mengekspor";
	}

	@Override
	public String exportacionTardara() {
		// TODO: metode dihasilkan secara otomatis
		return "Ekspor Akan Lama";
	}

	@Override
	public String porFavorEspere() {
		// TODO: metode dihasilkan secara otomatis
		return "Silakan Tunggu";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Anda tidak memiliki binari VLC. Anda memerlukan binari VLC untuk WaterMedia. Anda perlu menginstal secara manual dari https://www.videolan.org/vlc/.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "Unduh VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Kesalahan kritis: Nama modul '" + nombreModulo
				+ "' berisi karakter yang tidak valid. Bagian '" + parteInvalida
				+ "' bukan identitas Java yang valid. Ini terjadi ketika mod menggunakan kata-kata yang dicadangkan Java (seperti 'true', 'class') atau karakter yang tidak diizinkan dalam namanya.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "Karakter Tidak Valid dalam Nama Mod";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "Nama mod '" + nombreModulo + "' tidak valid karena berisi '" + parteInvalida
				+ "', yang merupakan kata-kata yang dicadangkan Java atau karakter yang tidak diizinkan. "
				+ "Cari di log mod mana yang sesuai dengan nama ini (biasanya nama file JAR)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "Akses folder mod dan edit file <b>mods.toml</b> di dalam folder <b>/META-INF/</b>. "
				+ "Ubah nilai <b>modId</b> untuk menggunakan hanya huruf, angka, dan garis bawah, tanpa kata-kata yang dicadangkan Java";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "Dengantoh nama yang valid: 'truemod_shot_enchantment' bukan 'true.shot.enchantment'. "
				+ "Ingat bahwa nama mod tidak dapat berisi titik, garis hubung, atau kata-kata yang dicadangkan Java seperti 'true', 'false' atau 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Kesalahan kritis dengan mod: '" + nombreJar
				+ "'. Bidang wajib 'mandatory' hilang dalam dependensinya. Ini terjadi ketika file mods.toml tidak menentukan apakah dependensi wajib.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Dependensi Mod dengan Bidang Wajib Hilang";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "Mod yang bermasalah adalah: <b>" + nombreJar
				+ "</b>. File ini memiliki kesalahan dalam konfigurasi dependensinya";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "Buka file <b>mods.toml</b> dalam folder <b>/META-INF/</b> dari mod <b>" + nombreJar + "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "Di bagian dependensi, pastikan setiap entri menyertakan <b>mandatory=true</b> atau <b>mandatory=false</b> (ex: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Kesalahan kritis dengan mod: '" + nombreJar
				+ "'. Konfigurasi access transformer tidak valid. Ini terjadi ketika file konfigurasi memiliki sintaks yang salah atau referensi ke kelas/metode yang tidak ada.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Pengubah Akses Tidak Valid";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "Mod yang bermasalah adalah: <b>" + nombreJar
				+ "</b>. Mod ini berisi konfigurasi access transformer yang tidak valid";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "Buka file <b>accessTransformer.cfg</b> dalam mod <b>" + nombreJar
				+ "</b> (biasanya dalam folder root file JAR)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "Perbaiki sintaks access transformer. Baris harus mengikuti format: <b>access class.method</b> (ex: public net.minecraft.world.entity.Entity.func_200560_a). Hapus baris dengan referensi ke kelas atau metode yang tidak ada di versi Minecraft Anda";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kesalahan kritis: Perbedaan antara ID mod di anotasi @Mod dan file mods.toml. Mod '" + nombreMod
				+ "' tidak dapat dimuat karena ID tidak cocok.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "Perbedaan antara @Mod dan mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "Mod dalam pengembangan '" + nombreMod
				+ "' memiliki perbedaan antara ID di anotasi <b>@Mod</b> dan nilai di <b>mods.toml</b>";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "Verifikasi bahwa ID di kelas utama Anda cocok dengan nilai <b>modId</b> di file <b>/META-INF/mods.toml</b>. Dengantoh: <b>@Mod(\"mimod\")</b> harus cocok dengan <b>modId=\"mimod\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "Jika Anda menggunakan Gradle, jalankan <b>clean</b> setelah membuat perubahan untuk memastikan sumber daya diperbarui dengan benar. Terkadang file lama tetap dalam folder build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "klien" : "server";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "server" : "klien";

		return "<b style='color:#" + config.obtenerColorError() + "'>Kesalahan kritis: Kelas '" + nombreClase
				+ "' sedang dimuat di lingkungan " + plataforma + ", tetapi dirancang untuk " + plataformaOpuesta
				+ ". <b>Gunakan fungsi 'Pohon Mod' di sidebar untuk mencari mod mana yang mencoba memuat kelas ini</b>. "
				+ "Mod dibangun khusus untuk satu platform dan tidak berfungsi di yang lain.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "Mod di Platform yang Salah";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "Di tab <b>Pohon Mod</b> (di sebelah kanan), cari referensi ke kelas <b>" + nombreClase
				+ "</b> untuk mengidentifikasi mod mana yang menyebabkan masalah";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "klien" : "server";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "server" : "klien";

		return "Mod yang diidentifikasi adalah mod <b>" + plataformaOpuesta
				+ "</b> dan tidak boleh digunakan di lingkungan " + plataforma + " Anda.";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "Hapus mod yang bermasalah dari folder <b>mods</b> Anda. Jika Anda memerlukan fungsionalitas serupa untuk platform ini, "
				+ "cari mod alternatif yang dirancang khusus untuk <b>klien</b> atau <b>server</b> sesuai kebutuhan";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Kesalahan kritis: Metadata untuk modid '").append(modIdFaltante).append("' hilang. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("Mod berikut mungkin menyebabkan masalah: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", dan lainnya...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Ini terjadi ketika mod tergantung pada mod lain yang tidak diinstal atau memiliki file mods.toml yang salah.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "Metadata mods.toml Hilang";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("Mod berikut tergantung pada '").append(modIdFaltante)
					.append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", dan lainnya...");
			paso.append("</b>. Gunakan fungsi <b>Pohon Mod</b> untuk mengkonfirmasi mod mana yang menyebabkan masalah");
			return paso.toString();
		}
		return "Mod sedang mencoba bergantung pada '" + modIdFaltante
				+ "', tetapi mod ini tidak diinstal. Gunakan fungsi <b>Pohon Mod</b> untuk mengidentifikasi mod mana yang menyebabkan masalah";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Anda memiliki dua opsi:<br/>" + "1. <b>Instal mod yang hilang</b>: Cari dan instal mod dengan ID '"
				+ modIdFaltante + "'<br/>"
				+ "2. <b>Hapus mod yang tergantung</b>: Jika Anda tidak memerlukan fungsi ini, hapus mod yang bergantung pada '"
				+ modIdFaltante + "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Jika mod '" + modIdFaltante + "' adalah library (seperti 'forge', 'minecraft', 'curios'), "
				+ "pastikan Anda memiliki versi Minecraft dan Forge yang benar diinstal. "
				+ "Jika itu adalah mod biasa, cari di halaman downloadnya persyaratan awal yang diperlukan";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Peringatan: Kesalahan saat menginisialisasi sistem suara. Suara dan musik telah dinonaktifkan. Kesalahan ini umumnya terkait dengan mod SoundPhysicsMod dan dapat disebabkan oleh konflik dengan library suara lain.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "Kesalahan dalam Sistem Suara";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "Kesalahan ini umumnya terkait dengan <b>SoundPhysicsMod</b>. Verifikasi bahwa Anda memiliki versi terbaru yang kompatibel dengan versi Minecraft Anda";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "Jika Anda menggunakan mod suara lain (seperti Sound Filters, Dynamic Surroundings, dll.), coba hapus SoundPhysicsMod sementara untuk melihat apakah itu mengatasi konflitk";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "Periksa folder <b>logs</b> untuk menemukan pesan tambahan yang terkait dengan LWJGL atau OpenAL, yang dapat mengindikasikan masalah dengan library suara yang mendasar";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Kesalahan kritis: Kelas '").append(nombreClase)
				.append("' terdaftar sebagai listener acara tetapi tidak berisi metode yang valid. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Kelas ini ditemukan dalam mod berikut: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", dan lainnya...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Ini terjadi ketika kelas terdaftar untuk mendengarkan acara tanpa memiliki metode yang dianotasi dengan @SubscribeEvent.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "Kelas Terdaftar tanpa Listener Acara";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("Kelas yang bermasalah ditemukan dalam mod ini: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", dan lainnya...");
			paso.append("</b>. Mod ini mencoba mendaftarkan acara tanpa metode yang valid");
			return paso.toString();
		}
		return "Kelas <b>" + nombreClase
				+ "</b> terdaftar untuk mendengarkan acara tetapi tidak berisi metode dengan anotasi <b>@SubscribeEvent</b>. Gunakan fungsi <b>Pohon Mod</b> untuk mengidentifikasi mod mana yang berisi kelas ini";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "Di kode sumber, verifikasi bahwa kelas <b>" + nombreClase
				+ "</b> berisi setidaknya satu metode dengan: "
				+ "<b>@SubscribeEvent public void namaMetode(AcaraSpesifik acara) { ... }</b>. "
				+ "Jika itu kelas internal, pastikan tidak ditandai sebagai statis";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("Untuk mod yang diidentifikasi (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", dll.");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("hubungi pengembang mod ini untuk memperbaiki masalahnya. ");
			} else {
				paso.append("hubungi pengembang mod ini untuk memperbaiki masalahnya. ");
			}
		}

		paso.append(
				"Jika Anda adalah pengembang, hapus pendaftaran kelas ini di EventBus atau tambahkan metode @SubscribeEvent yang valid");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreFile) {
		String mensaje = "<b style='color:#" + config.obtenerColorError()
				+ "'>Kesalahan kritis: Kesalahan di UnionFileSystem saat memproses '" + nombreFile + "'. ";

		mensaje += "Kesalahan ini sangat umum dalam modpack yang dikonfigurasi sebelumnya dan secara langsung terkait dengan masalah launcher. ";

		mensaje += "Sistem tidak dapat membaca file mod dengan benar karena rusak atau tidak lengkap.</b>";

		return mensaje;
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "Kesalahan UnionFileSystem - File Rusak";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreFile) {
		String paso = "Kesalahan spesifik <b>cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException</b> terdeteksi dengan file <b>"
				+ nombreFile + "</b>.";

		paso += " Ini adalah kesalahan yang diketahui di launcher modpack ketika file tidak diunduh sepenuhnya.";

		return paso;
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "Instal ulang modpack sepenuhnya. Kesalahan ini terjadi terutama ketika launcher tidak menyelesaikan download semua file. "
				+ "Jika Anda menggunakan <b>Luna Pixel</b>, kami sangat merekomendasikan menggunakan <b>ATLauncher</b> sebagai gantinya, "
				+ "karena launcher ini menangani file mod dengan lebih baik dan menghindari kesalahan spesifik ini.";

	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "Jika masalah berlanjut setelah reinstal: <br/>" + "1. <b>Beralih ke launcher lain</b> <br/>"
				+ "2. Jika menggunakan <b>Luna Pixel</b>, <b>gunakan ATLauncher</b> yang lebih dapat diandalkan untuk menghindari kesalahan spesifik ini<br/>"
				+ "3. Verifikasi koneksi internet dan ruang disk Anda sebelum menginstal ulang modpack";

	}

	/**
	 * Mengambil pesan konfirmasi untuk mengaktifkan proxy dari
	 * System.out/System.err
	 * 
	 * @return Pesan penjelas dengan peringatan dan persyaratan
	 */
	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "Aktifkan ProxySysOutSysErr?\n\n" + "Opsi ini memberikan " + Statics.nombre_cd.obtener()
				+ " akses ke System.out dan System.err ketika launcher tidak menyediakan log.\n\n"
				+ "Hanya boleh diaktifkan ketika Anda tidak dapat menempel log secara manual.\n\n"
				+ "Peringatan: Ini dapat mengganggu beberapa mod atau launcher.\n\n"
				+ "Diperlukan restart game/app agar perubahan berlaku efektif.";
	}

	/**
	 * Mengambil judul untuk dialog konfirmasi
	 * 
	 * @return Judul berbahasa Indonesia untuk jendela konfirmasi
	 */
	@Override
	public String confirmacionTitulo() {
		return "Konfirmasi";
	}

	/**
	 * Mengambil pesan keberhasilan setelah mengaktifkan proxy
	 * 
	 * @return Pesan informatif tentang status proxy
	 */
	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr diaktifkan dengan benar.\n\n" + "Diperlukan restart " + Statics.nombre_cd.obtener()
				+ " agar perubahan berlaku efektif.";
	}

	/**
	 * Mendapatkan judul untuk dialog informatif
	 * 
	 * @return Judul berbahasa Indonesia untuk jendela informasi
	 */
	@Override
	public String informacionTitulo() {
		return "Informasi";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("Kesalahan kritis: AzureLib dan GeckoLib diinisialisasi terlalu cepat! ");
		} else if (azureLibError) {
			mensaje.append("Kesalahan kritis: AzureLib diinisialisasi terlalu cepat! ");
		} else if (geckoLibError) {
			mensaje.append("Kesalahan kritis: GeckoLib diinisialisasi terlalu cepat! ");
		}

		mensaje.append(
				"Kesalahan ini terjadi ketika Anda mencoba menggunakan mod Fabric dengan versi non-Fabric dari library ini. ");

		if (connectorPresente) {
			mensaje.append(
					"Mod kompatibilitas terdeteksi (Sinytra Dengannector atau specialcompatibilityoperation), yang menunjukkan bahwa Anda mencoba menggunakan mod Fabric di lingkungan Forge atau FeatureCreep. ");
			mensaje.append(
					"Periksa kesalahan 'Kesalahan Inisialisasi FabricMC' di log untuk mengidentifikasi mod spesifik mana yang menyebabkan masalah. ");
		}

		mensaje.append(
				"AzureLib dan GeckoLib adalah library penting untuk mod animasi, tetapi harus cocok dengan platform yang benar (Fabric atau Forge). ");
		mensaje.append("Game tidak dapat memuat mod animasi dengan benar karena konflik inisialisasi ini.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Library Diinisialisasi Terlalu Cepat";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "Periksa kesalahan 'Kesalahan Inisialisasi FabricMC' untuk mengidentifikasi mod yang bermasalah";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "Verifikasi bahwa Anda menggunakan versi yang benar dari AzureLib/GeckoLib untuk platform Anda (Forge atau Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kesalahan kritis: Ketidaksesuaian antara C2ME dan mod koneksi. "
				+ "Kesalahan ini terjadi karena C2ME mencoba mengakses komponen Java internal yang dibatasi di lingkungan dengan "
				+ "Sinytra Dengannector atau specialcompatibilityoperation atau mod kompatibilitas Fabric/Forge lainnya. "
				+ "<b>C2ME tidak kompatibel dengan lingkungan ini, tetapi <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> adalah alternatif yang direkomendasikan</b> yang berfungsi dengan benar "
				+ "dengan mod koneksi. Game tidak dapat dimulai karena konflik izin keamanan Java ini.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "Ketidaksesuaian C2ME dengan Mod Koneksi";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "Hapus C2ME dari folder mod Anda";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "Unduh dan instal <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> sebagai gantinya (kompatibel dengan Sinytra Dengannector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "Verifikasi bahwa semua mod koneksi (seperti Sinytra Dengannector) diperbarui ke versi terbaru mereka";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kesalahan kritis: Gagal memuat plugin JEI untuk mod '" + modId + "'. Kelas '" + nombreClase
				+ "' (ID plugin: '" + pluginId + "') menghasilkan kesalahan yang menyebabkan game macet saat startup. "
				+ "Masalah ini terjadi ketika mod memiliki integrasi JEI yang tidak kompatibel atau rusak yang mengganggu inisialisasi game.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "Plugin JEI Gagal - Menyebabkan Crash";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "Mod <b>" + modId
				+ "</b> berisi plugin JEI yang rusak yang menyebabkan crash. Gunakan fungsi <b>Pohon Mod</b> untuk mengkonfirmasi mod mana yang menghasilkan masalah";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "Hapus sementara mod <b>" + modId
				+ "</b> dari folder mod Anda untuk memverifikasi apakah itu mengatasi crash";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "Cari pembaruan untuk mod <b>" + modId
				+ "</b> atau hubungi pengembangnya melaporkan masalah dengan plugin JEI. "
				+ "Sementara itu, mod harus dihapus untuk dapat memulai game";
	}

	/**
	 * Mengambil judul aplikasi
	 * 
	 * @return Judul jendela principal
	 */
	@Override
	public String tituloLectador() {
		return "Pembaca Konsol - Crash Detector";
	}

	/**
	 * Mengambil judul untuk legenda warna
	 * 
	 * @return Judul bagian legenda
	 */
	@Override
	public String obtenerTituloLeyenda() {
		return "LEGENDA WARNA";
	}

	/**
	 * Mengambil teks untuk mengidentifikasi kesalahan pada legenda
	 * 
	 * @return Teks deskriptif para kesalahanes
	 */
	@Override
	public String obtenerErrorEnLeyenda() {
		return "Kesalahan kritis";
	}

	/**
	 * Mengambil teks untuk mengidentifikasi stack trace pada legenda
	 * 
	 * @return Teks deskriptif para stacktraces
	 */
	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Stack trace";
	}

	/**
	 * Mengambil judul untuk jendela kesalahan
	 * 
	 * @return Judul standar untuk pesan kesalahan
	 */
	@Override
	public String obtenerTituloError() {
		return "Kesalahan";
	}

	/**
	 * Mendapatkan pesan untuk kesalahan saat memproses baris log
	 * 
	 * @return Pesan kesalahan específico
	 */
	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "Terjadi kesalahan saat memproses baris yang dipilih";
	}

	/**
	 * Mengambil judul untuk area nama kesalahan
	 * 
	 * @return Judul panel nama kesalahan
	 */
	@Override
	public String obtenerNombreError() {
		return "NAMA KESALAHAN";
	}

	/**
	 * Mengambil judul untuk area deskripsi kesalahan
	 * 
	 * @return Judul panel deskripsi kesalahan
	 */
	@Override
	public String obtenerDescripcionError() {
		return "DESKRIPSI DETAIL";
	}

	/**
	 * Mengambil judul untuk pemilih konsol
	 * 
	 * @return Judul kotak kombo pemilihan
	 */
	@Override
	public String obtenerSeleccionarConsola() {
		return "PILIH KONSOL";
	}

	/**
	 * Mengambil nama bawaan untuk kesalahan
	 * 
	 * @return Nama umum untuk kesalahan
	 */
	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "Kesalahan tidak diidentifikasi";
	}

	/**
	 * Mengambil deskripsi bawaan untuk kesalahan
	 * 
	 * @return Deskripsi umum untuk kesalahan
	 */
	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "Kesalahan kritis terdeteksi dalam log. " + "Periksa stack trace untuk mengidentifikasi akar penyebab.";
	}

	/**
	 * Mengambil pesan untuk kesalahan pembacaan berkas
	 * 
	 * @return Pesan spesifik untuk kegagalan pembacaan
	 */
	@Override
	public String obtenerErrorLecturaArchivo() {
		return "Tidak dapat membaca file log. " + "Verifikasi bahwa file ada dan memiliki izin baca.";
	}

	/**
	 * Mendapatkan label untuk tombol di bilah samping
	 * 
	 * @return Teks yang akan muncul di tombol samping
	 */
	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Penganalisis Log";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Kesalahan kritis: Gagal mendaftarkan subscriber otomatis untuk mod '").append(modId)
				.append("'. ");

		mensaje.append("Kelas yang bermasalah: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Kelas ini ditemukan di: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", dan lainnya...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Kesalahan ini terjadi ketika mod mencoba mendaftarkan kelas sebagai subscriber otomatis tetapi kelas tidak dapat dimuat dengan benar. ");
		mensaje.append(
				"<b>Periksa kesalahan lain dalam log, karena masalah nyata mungkin berada di tempat lain dalam log</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "Gagal dalam Pendaftaran Subscriber Otomatis";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "Mod <b>" + modId + "</b> mencoba mendaftarkan kelas <b>" + nombreClase
				+ "</b> sebagai subscriber otomatis, tetapi gagal. Verifikasi bahwa kelas ini ada dan dapat diakses";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"Kelas yang bermasalah <b>" + nombreClase + "</b> ditemukan dalam file ini: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", dan lainnya...");
			paso.append("</b>. ");
			paso.append(
					"Gunakan fungsi <b>Pohon Mod</b> untuk mengkonfirmasi file mana yang secara spesifik berisi kelas yang bermasalah");
			return paso.toString();
		}
		return "Kelas <b>" + nombreClase + "</b> tidak ditemukan dalam file mod apa pun. Verifikasi bahwa mod <b>"
				+ modId
				+ "</b> diinstal dengan benar. Gunakan fungsi <b>Pohon Mod</b> untuk membantu mengidentifikasi masalah";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "Perbarui mod <b>" + modId
				+ "</b> ke versi terbaru yang kompatibel dengan versi Minecraft dan Forge Anda. "
				+ "Jika masalah berlanjut, hubungi pengembang mod melaporkan kesalahan dengan kelas yang bermasalah";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "Periksa <b>kesalahan lain dalam log</b> sebelum pesan ini, karena masalah nyata mungkin berada di tempat lain dalam log. "
				+ "Kadang-kadang kesalahan sebelumnya mencegah kelas yang diperlukan memuat dengan benar untuk pendaftaran subscriber";
	}

	@Override
	public String limpiado() {
		// TODO: metode dihasilkan secara otomatis
		return "Dibersihkan";
	}

	@Override
	public String original() {
		// TODO: metode dihasilkan secara otomatis
		return "Asli";
	}

	@Override
	public String verEnConsola() {
		// TODO: metode dihasilkan secara otomatis
		return "Lihat di Konsol";
	}

	@Override
	public String advertencia() {
		// TODO: metode dihasilkan secara otomatis
		return "Peringatan";
	}

	@Override
	public String fatal() {
		// TODO: metode dihasilkan secara otomatis
		return "Fatal";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO: metode dihasilkan secara otomatis
		return "BattlyLauncher tidak memiliki log atau konsol untuk disalin. Anda dapat menggunakan ProxySysOutSysErr untuk mengintersep STDOUT dan STDERR dan restart game tetapi ProxySysOutSysErr dapat bertentangan dengan mod yang memodifikasi STDOUT atau STDERR";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO: metode dihasilkan secara otomatis
		return "Anda perlu mengaktifkan mode debug dalam konfigurasi NightWorld untuk mendapatkan log launcher. Sangat penting terutama karena memiliki STDOUT dan STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO: metode dihasilkan secara otomatis
		return "Anda perlu menyimpan atau menempel konten Terminal server Anda karena memiliki informasi tidak di log lain termasuk STDOUT, STDERR, dan kesalahan lainnya. Silakan tempel konten sesi terakhir. Untuk masa depan, Anda dapat menyimpan konten terminal ke file cd_launcherlog Untuk menghindari harus menempelnya, tambahkan >> cd_launcherlog setelah perintah, seperti yang ditunjukkan dalam gambar. Perhatikan bahwa ini akan mencegah ditampilkan di terminal; itu hanya akan muncul di file itu setelah selesai.";
	}

// Metode Idioma yang terkait dengan verifikasi LexForgeMLTransformerEnNeoForge

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("Kesalahan kritis: transformer LexForge terdeteksi di lingkungan NeoForge. ");
		sb.append("</b>");

		sb.append("Kelas yang terlibat: <b>").append(claseReceptora).append("</b>. ");
		sb.append("Interface yang terpengaruh adalah <b>").append(interfazObjetivo).append("</b> ");
		sb.append("dan metode <b>").append(firmaMetodoFaltante).append("</b> hilang. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Kelas ditemukan di: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", dan lainnya...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"Tidak ada JAR yang ditemukan yang berisi kelas itu; itu mungkin diarsir atau disertakan sebagai jar-in-jar. ");
		}

		sb.append(
				"Kegagalan ini muncul ketika transformer/layanan ModLauncher yang dikompilasi untuk MinecraftForge/LexForge ");
		sb.append("dimuat di bawah NeoForge dengan versi API ModLauncher yang tidak kompatibel. ");
		sb.append("Perbarui atau ganti komponen untuk NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "Transformer LexForge digunakan di NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "Identifikasi transformer yang tidak kompatibel: <b>" + claseReceptora + "</b>. "
				+ "API yang diharapkan adalah <b>" + interfazObjetivo + "</b> dan <b>" + firmaMetodoFaltante
				+ "</b> hilang. "
				+ "Periksa apakah mod mendaftarkan kelas ini di <b>META-INF/services</b> dan hapus atau nonaktifkan di NeoForge.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Lokasi mod yang terlibat: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", dan lainnya...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"Tidak ada JAR yang ditemukan yang berisi kelas. Periksa jar-in-jar dan dependensi yang diarsir. ");
		}
		sb.append(
				"Hapus sementara JAR tersebut atau gunakan versi yang kompatibel dengan NeoForge untuk memastikan asal muasalnya.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "Ganti komponen dengan versi khusus NeoForge atau kompilasi ulang terhadap "
				+ "versi ModLauncher yang digunakan oleh NeoForge. Hindari binari LexForge/MinecraftForge yang lama.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Bersihkan folder mod dan hapus jar-in-jar duplikat. Kosongkan cache launcher jika diperlukan "
				+ "dan restart untuk memverifikasi bahwa transformer LexForge tidak dimuat.";
	}
// Di kelas bahasa Anda:

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia tidak dapat dimulai: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("tidak kompatibel.</b> ");
		sb.append("Hapus Xenon dan gunakan Embeddium atau Sodium. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Terdeteksi di: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", dan lainnya...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia tidak kompatibel dengan Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "Terdeteksi " + label + " tidak kompatibel dengan WaterMedia. Hapus dari profil.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("Lokasi: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", dan lainnya...");
			sb.append("</b>. Hapus JAR itu.");
			return sb.toString();
		}
		return "Tidak ada JAR yang ditemukan. Periksa folder mods dan hapus Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Instal Embeddium atau Sodium sebagai pengganti dan restart game.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "Kesalahan kompresi (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater ditutup selama penyalinan sumber daya TACZ.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Terkait dengan: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", dan lainnya");
			sb.append("</b>. ");
		}
		sb.append("<br/><b>Solusi:</b> di <code>tacz/tacz-pre.toml</code> atur <code>DefaultPackDebug=true</code>. ")
				.append("Jika diperlukan, buat peta terlebih dahulu kemudian aktifkannya.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "Di tacz/tacz-pre.toml atur DefaultPackDebug=true. Jika diperlukan, buat peta terlebih dahulu kemudian aktifkannya.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Fungsi kepadatan tidak terikat";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>Fungsi kepadatan hilang dari registry.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("Hilang: ");
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
				"<br/><b>Solusi:</b> instal atau aktifkan mod/datapack yang mendefinisikan fungsi tersebut dan restart. Alasan umum lainnya untuk masalah ini adalah ketika Anda memiliki mod yang diperlukan, tetapi memiliki masalah atau konflik dengan mod lain; misalnya, Terralith memiliki banyak masalah dan dapat menyebabkan kesalahan ini dan lainnya, termasuk kesalahan JSON.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "Instal atau aktifkan mod/datapack yang menyediakan fungsi tersebut dan restart game.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// Pesan singkat, dengan warna kesalahan, yang secara eksplisit menyebutkan mod
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Entrada de registro no presente: ").append(claveFaltante).append(". ");
		sb.append("Frecuente con la alfa de Steam & Railways para Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (alpha)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Hapus atau ganti versi alfa Steam & Railways untuk Create 6 dengan versi yang kompatibel.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// Singkat, dengan warna kesalahan dan rekomendasi langsung
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Denganflicto de carga: Multiworld junto con Sodium/Embeddium/Rubidium provoca ")
				.append("IncompatibleClassChangeKesalahan (FabricLoader.getInstance). ")
				.append("Sugerencia: quita Multiworld o el mod de rendimiento, o usa versiones compatibles.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "Konflik: Multiworld dengan mod performa";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Copot Multiworld atau Sodium/Embeddium/Rubidium, atau perbarui ke versi yang kompatibel satu sama lain.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sodium mendeteksi driver grafis yang tidak kompatibel. "
				+ "Perbarui driver GPU Anda ke versi minimum yang diperlukan atau ikuti panduan Sodium." + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "Kesalahan konteks OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL gagal: tidak ada konteks saat ini atau fungsi tidak tersedia di konteks ini. "
				+ "Ini juga bisa menjadi masalah driver video." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "Perbarui/instal ulang driver GPU dan restart; nonaktifkan overlay dan coba tanpa mod performa.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "Tautan disalin ke papan klip.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		// TODO: metode dihasilkan secara otomatis
		return "Cari dalam kompresi (.zip/.jar/.war/.ear/.fpm/.rar Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kesalahan resolusi tekstur: Tidak dapat menyesuaikan " + recurso + " - ukuran: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "Kesalahan Resolusi Tekstur";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "Kesalahan ini terjadi ketika tekstur terlalu besar atau ada terlalu banyak paket sumber daya. "
				+ "Coba gunakan paket sumber daya resolusi lebih rendah atau hapus beberapa paket sumber daya. "
				+ "Verifikasi bahwa Anda belum menambahkan tekstur kustom dengan resolusi lebih besar dari yang diizinkan.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kesalahan layanan ModLauncher: Jalur dengan karakter tidak valid. "
				+ "Layanan ModLauncher tidak dapat memproses jalur yang berisi karakter non-ASCII atau karakter khusus. "
				+ "Karakter bermasalah termasuk: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요, dan terutama karakter '\"' ketika berada di akhir nama. "
				+ "Komponen layanan umum di ModLauncher termasuk " + Statics.nombre_cd.obtener() + ", "
				+ "FeatureCreep, Vivicraft, Optifine, Sodium, clone, Iris Shaders/Oculus, MixerLogger, CrashAssistant dan Sintrya Connector. "
				+ "Anda dapat menghapus semua layanan, tetapi masalah lain dapat muncul karena nama jalur. "
				+ "Solusi: Ganti nama instance untuk menggunakan hanya karakter ASCII (a-z, A-Z, 0-9), tanpa spasi atau karakter khusus.</b>";
	}// TODO incluye un Caridor para mods con servicios

	@Override
	public String nombre_error_modlauncher_path() {
		return "Kesalahan Jalur di ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "Kesalahan ini terjadi ketika jalur instance berisi karakter non-ASCII atau karakter khusus. "
				+ "Layanan ModLauncher tidak dapat menangani jalur ini. "
				+ "Solusi: Ganti nama instance untuk menggunakan hanya karakter ASCII (a-z, A-Z, 0-9) dan hindari spasi dan karakter khusus. "
				+ "Perhatian khusus pada karakter '\"' yang sangat bermasalah, terutama ketika berada di akhir nama.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Editor Kode";
	}

	@Override
	public String nuevo() {
		return "Baru";
	}

	@Override
	public String actualizarSeleccionado() {
		return "Perbarui yang dipilih";
	}

	@Override
	public String eliminarSeleccionado() {
		return "Hapus yang dipilih";
	}

	@Override
	public String exportarJSON() {
		return "Ekspor JSON...";
	}

	@Override
	public String guardarTodo() {
		return "Simpan semua";
	}

	@Override
	public String general() {
		return "General";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String faltanCampos() {
		return "Lengkapi semua bidang umum yang wajib.";
	}

	@Override
	public String critInvalida() {
		return "Tingkat keparahan tidak valid. Gunakan ADVERTENCIA, ERROR, atau FATAL.";
	}

	@Override
	public String filtroNoExiste() {
		return "Filter yang ditentukan tidak ada.";
	}

	@Override
	public String faltanIdiomas() {
		return "Lengkapi nama dan hasil untuk semua bahasa:";
	}

	@Override
	public String verificacionInvalida() {
		return "Sebuah pemeriksaan tidak valid. Periksa kembali kolomnya.";
	}

	@Override
	public String guardadoOk() {
		return "Disimpan dengan benar.";
	}

	@Override
	public String editorCodiceBoton() {
		return "Tambah alasan";
	}

	@Override
	public String descripcionEditorCodice() {
		// TODO: metode dihasilkan secara otomatis
		return "Anda dapat mendaftarkan alasan di sini. Anda membutuhkan ID, string tanpa karakter khusus atau aksen atau spasi. Untuk filter Anda dapat menggunakan \"baris berisi\" untuk mencari string dalam baris, \"semua berisi\" jika log memiliki string, \"regex baris\" jika baris memiliki regex, dan \"regex semua\" lebih disarankan gunakan versi baris. Anda perlu menetapkan kritikalitas, FATAL, ERROR, atau PERINGATAN untuk warna. Untuk semua bahasa Anda perlu menulis nama dan jawaban untuk layar.Anda dapat menambah lebih banyak pemeriksaan atau menghapus yang lain. Simpan saat selesai.";
	}

	@Override
	public String descartarCambios() {
		return "Buang perubahan tidak disimpan dalam verifikasi saat ini?";
	}

	@Override
	public String confirmacion() {
		return "Konfirmasi";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "Apakah Anda ingin menyimpan perubahan sebelum keluar?";
	}

	@Override
	public String salirSinGuardar() {
		return "Keluar tanpa menyimpan";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Kesalahan kritis: Gagal memuat layanan modlauncher (IDependencyLocator).<br>");
		sb.append("🔹 <b>Kelas bermasalah:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>Mod yang terpengaruh:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append(
					"🔸 <b>Mod tidak teridentifikasi.</b> Periksa mod baru-baru ini, pengembangan, atau dikemas dengan buruk.<br>");
		}

		sb.append("🔸 <b>Penyebab:</b> File <code>META-INF/services/...</code> mod rusak, ");
		sb.append("tidak kompatibel dengan versi Forge/NeoForge ini, atau mod untuk versi yang salah.<br>");
		sb.append("🔸 <b>Akibat:</b> Forge/NeoForge tidak dapat mendaftarkan dependensi mod, ");
		sb.append("yang mencegah start game.<br>");
		sb.append("🔸 <b>Solusi:</b> Perbarui, instal ulang, atau hapus mod yang bermasalah. ");
		sb.append(
				"Jika Anda menggunakan mod pengembangan, pastikan dikompilasi untuk versi Forge/NeoForge spesifik Anda.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "Kesalahan Konfigurasi Layanan (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. Identifikasi mod penyebabnya: periksa mod yang diinstal baru-baru ini atau pengembangan.";
		}
		return "1. Mod yang bermasalah adalah: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. Perbarui, instal ulang, atau hapus mod. Pastikan Anda menggunakan versi yang kompatibel dengan Forge/NeoForge Anda.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888"; // Abu-abu netral untuk nama kelas/bidang

		return "<b style='color:#" + colorError + "'>Kesalahan kritis: Bidang tidak ada.</b><br>"
				+ "Mod mencoba mengakses bidang <b style='color:#" + colorCodigo + "'>" + campo + "</b>, "
				+ "yang tidak ada di versi game ini atau mod lain.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "Bidang Tidak Ada (NoSuchFieldKesalahan)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. Kesalahan ini biasanya terjadi ketika mod tidak kompatibel dengan versi game saat ini atau mod lain.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888"; // Gris para metodos/clases

		return "<b style='color:#" + colorError + "'>Kesalahan kritis: Metode tidak ada.</b><br>"
				+ "Mod mencoba memanggil metode <b style='color:#" + colorCodigo + "'>" + metodo + "</b>, "
				+ "yang tidak ada di versi game ini atau mod lainnya.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String restablecerPlantilla() {
		// TODO: metode dihasilkan secara otomatis
		return "Atur Ulang Template";
	}

	@Override
	public String restablecer() {
		// TODO: metode dihasilkan secara otomatis
		return "Atur Ulang";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		// TODO: metode dihasilkan secara otomatis
		return "Atur ulang " + nombreImagen + " ke nilai default?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		// TODO: metode dihasilkan secara otomatis
		return "Atur ulang template ke nilai default?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		// TODO: metode dihasilkan secara otomatis
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kelas AzureLib tidak ada. Jika Anda sudah memiliki AzureLib, silakan instal versi sebelum 8 Oktober 2025. Ini umum terjadi. Jika Anda tidak memiliki AzureLib, instal versi terbaru.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Mod <code>healight</code> menyebabkan kesalahan kritis: <code>java.lang.NoSuchFieldKesalahan: INT</code>. "
				+ "Kesalahan ini terjadi karena mod mencoba mengakses bidang yang tidak lagi ada di versi MCForge 47.10 Minecraft 1.20+. "
				+ "Game tidak dapat dimulai karena masalah ini.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• Hapus atau perbarui mod <code>healight</code>. "
				+ "Versi saat ini tidak kompatibel dengan MinecraftForge 47.10 untuk 1.20.1. "
				+ "Cari versi mod yang lebih baru atau pertimbangkan menggunakan alternatif.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "Kesalahan kritis: healight - Bidang 'INT' tidak ditemukan";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("Kelas <code>").append(clase)
				.append("</code> tidak mengimplementasikan metode yang diperlukan:<br>").append("<code>").append(metodo)
				.append("</code><br>").append("dari antarmuka <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>Mod atau file yang dicurigai: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• Kesalahan ini terjadi ketika mod mengimplementasikan antarmuka tetapi melewatkan metode wajib. "
				+ "Perbarui <b>kedua mod</b> yang terlibat (yang mendefinisikan antarmuka dan yang mengimplementasikannya). "
				+ "Jika Anda tidak tahu mana, cari nama yang muncul dalam pesan kesalahan.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "Metode antarmuka tidak diimplementasikan (AbstractMethodKesalahan)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Mod mencoba memuat kelas dari <b>sisi klien</b> "
				+ "(<code>AnimationMetadataSection</code>) di <b>server khusus</b>, yang tidak mungkin. "
				+ "Kesalahan ini biasanya muncul ketika mod tidak memisahkan kodenya dengan benar antara klien dan server. "
				+ "Kehadiran <code>ModernFix</code> dapat mengekspos masalah ini, meskipun bukan penyebab langsungnya.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>Opsi cepat:</b> Hapus sementara <code>ModernFix</code> untuk memastikan server dimulai. "
				+ "Jika berhasil, masalahnya ada di mod lain yang memuat kelas klien di server.<br>"
				+ "• <b>Solusi permanen:</b> Identifikasi mod yang bermasalah (cari mod dengan sumber daya animasi, tekstur khusus, atau perpustakaan grafis) dan perbarui atau hapusnya.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Kelas klien dimuat di server (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "File konfigurasi mod <code>Sinytra Dengannector</code> rusak. "
				+ "Ini biasanya terjadi ketika file penuh dengan karakter null (<code>\\u0000</code>) "
				+ "karena game ditutup tiba-tiba, kegagalan penulisan, atau konflik mod.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• Navigasi ke folder <code>config/</code> dari instance Minecraft Anda.<br>"
				+ "• Cari dan hapus konfigurasi mod connector.<br>"
				+ "• Restart game: Sinytra Dengannector akan menghasilkan file konfigurasi baru yang bersih.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Konfigurasi Sinytra Dengannector rusak";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "File <code>" + nombreJar
				+ "</code> rusak atau tidak lengkap.<br>"
				+ "Sistem tidak dapat membaca isinya karena kepala akhir file ZIP hilang.<br>"
				+ "Kesalahan ini biasanya terjadi setelah unduhan terputus atau kegagalan launcher.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "File JAR rusak (dengan nama spesifik)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>Hapus file yang rusak</b> dan download kembali dari sumber resmi (CurseForge, MinecraftStorage, dll.).<br>"
				+ "• Jika Anda menggunakan launcher seperti CurseForge, Technic, atau Luna Pixel, pertimbangkan untuk beralih ke <b>ATLauncher</b> atau <b>Prism Launcher</b>, "
				+ "yang memverifikasi integritas file dengan lebih baik.<br>"
				+ "• Pastikan koneksi internet Anda stabil selama download.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Tidak dapat memuat dunia karena salah satu file NBT-nya rusak "
				+ "(misalnya: <code>level.dat</code>, <code>playerdata/*.dat</code>, atau chunks).<br>"
				+ "Kesalahan spesifik adalah: <code>UTFDataFormatException: malformed input around byte " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ Sebelum mencoba perbaikan apa pun, buat cadangan lengkap folder dunia.</b><br><br>"
				+ "Anda dapat mencoba memperbaiki file yang rusak menggunakan <b>editor NBT</b> seperti <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>.<br>"
				+ "Jika kerusakan parah, gunakan <b>editor heksadesimal</b> (seperti HxD) untuk memeriksa dan memperbaiki byte yang tidak valid "
				+ "(hanya jika Anda berpengalaman dengan format NBT).<br>"
				+ "Sebagai pilihan terakhir, pulihkan dari cadangan atau gunakan <i>world repair</i> dari mod seperti <code>FTB Backup</code>.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>Buat cadangan lengkap folder dunia</b> sebelum mencoba perbaikan apa pun.<br>"
				+ "• Gunakan editor NBT (seperti NBT Studio) untuk membuka dan memperbaiki file yang rusak.<br>"
				+ "• Jika gagal, periksa file dengan editor heksadesimal di posisi byte yang rusak.<br>"
				+ "• Jika Anda tidak berpengalaman, pulihkan dari cadangan terakhir.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "Dunia rusak: kesalahan saat memuat data NBT";
	}

	@Override
	public String problema_con_openAL() {
		// TODO: metode dihasilkan secara otomatis
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki masalah dengan OpenAL. Terkadang driver Nouveau dapat menyebabkannya, tetapi terkadang versi OpenAL yang disertakan dalam aplikasi tidak kompatibel dengan versi di distribusi Anda dan Anda perlu menggunakan versi yang disediakan oleh distro Anda, khususnya umum dengan distribusi Red Hat dan dengan mod suara seperti Sound Physics Remastered. Konsultasikan panduan ini untuk bantuan lebih lanjut: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>Solusi masalah suara Minecraft di Linux</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Server tidak dapat dimulai karena file dunia terkunci oleh proses lain.<br>"
				+ "Ini biasanya terjadi jika:<br>" + "• Ada instans server lain yang sedang berjalan.<br>"
				+ "• Antivirus atau file explorer memiliki folder dunia terbuka.<br>"
				+ "• Proses sebelumnya tidak ditutup dengan benar dan meninggalkan file terkunci.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>Tutup semua instans server</b> (termasuk proses latar belakang seperti javaw.exe).<br>"
				+ "• Jika Anda menggunakan panel hosting (seperti Multicraft), restart server sepenuhnya dari panel.<br>"
				+ "• <b>Nonaktifkan antivirus Anda sementara</b> jika Anda curiga mengunci file.<br>"
				+ "• Pada sistem lokal, tutup window Windows Explorer yang menampilkan folder dunia.<br>"
				+ "• Jika masalah berlanjut, hapus file <code>session.lock</code> secara manual di dalam folder dunia (hanya jika Anda yakin tidak ada server lain yang aktif).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "File dunia terkunci oleh proses lain";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Mod mencoba memperluas kelas <code>"
				+ clasePadreFinal + "</code>, "
				+ "tetapi kelas ini sekarang <b>final</b> dan tidak dapat diturunkan.<br>"
				+ "Kelas yang bermasalah adalah: <code>" + claseHija + "</code>.<br><br>"
				+ "Ini biasanya terjadi ketika mod dikompilasi untuk versi lama Minecraft atau mod dasar lain, "
				+ "yang telah menandai kelas sebagai <code>final</code> dalam versi terbaru.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>Perbarui semua mod yang terlibat</b>, terutama mereka yang mungkin terkait dengan mod dasar yang disebutkan.<br>"
				+ "• Jika masalah berlanjut, cari versi mod yang kompatibel dengan versi Minecraft Anda saat ini dan dependensinya.<br>"
				+ "• Dalam beberapa kasus, menghapus sementara mod yang berisi kelas anak dapat membantu mengonfirmasi penyebabnya.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "Upaya mewarisi dari kelas final";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Anda menggunakan <b>Rubidium</b> (fork usang dari Sodium untuk Forge) bersama dengan <b>Iris atau Oculus</b>.<br>"
				+ "Dalam versi Minecraft terbaru (1.19.2+), "
				+ "Rubidium tidak mengikuti perkembangan Sodium dan dependensinya mengalami masalah.<br><br>"
				+ "Kesalahan ini juga dapat terjadi jika ada konflik antara mod performa (Sodium, Rubidium, Embeddium, Bedium, Xeonium, dll.) atau Iris Shaders dan mod lain.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>Hapus Rubidium</b> dari folder <code>mods</code> Anda.<br>"
				+ "• <b>Instal <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>, "
				+ "fork aktif dan kompatibel dari Sodium untuk Forge yang mendukung Iris/Oculus di 1.20+.<br>"
				+ "• Pastikan Anda tidak memiliki lebih dari satu fork Sodium yang terpasang secara bersamaan (misalnya: Rubidium + Embeddium).<br>"
				+ "• Jika Anda menggunakan Oculus bukan Iris, verifikasi bahwa juga kompatibel dengan versi Forge dan Embeddium Anda.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium usang dengan Iris/Oculus (OptionInstance adalah final)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Mod <code>Simple Voice Chat</code> tidak dapat memulai server suaranya karena "
				+ "port UDP sudah digunakan atau alamat IP yang dikonfigurasi tidak valid.<br>"
				+ "Hal ini tidak mencegah game untuk berjalan, tetapi menonaktifkan fungsi obrolan suara.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>Tutup instans Minecraft lain</b> atau aplikasi yang menggunakan port UDP 24454.<br>"
				+ "• Jika Anda di server, pastikan <b>tidak ada layanan lain</b> menggunakan port itu.<br>"
				+ "• Dalam konfigurasi mod (<code>config/voicechat/</code>), ubah port UDP ke port yang bebas (misalnya, 24455).<br>"
				+ "• Jika Anda menggunakan alamat IP kustom, verifikasi bahwa itu benar atau biarkan kosong untuk menggunakan default.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Voice Chat: port UDP sibuk atau IP tidak valid";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "BlockItem <code>" + nombreBlockItem
				+ "</code> memiliki blok nol.<br>"
				+ "Kesalahan ini biasanya terjadi pada <b>addon Create</b> (seperti <code>dndecor</code>, <code>createdeco</code>) "
				+ "ketika ada konflik dengan <code>Amendments</code>, <code>Moonshine</code>, atau inisialisasi blok yang salah.<br>"
				+ "<b>Catatan:</b> Ini bukan kesalahan Amendments secara langsung, tetapi gejala masalah yang lebih dalam dalam pemuatan pendaftaran.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>Perbarui semua mod terkait:</b> Create, Amendments, Moonshine, dan addon apa pun (terutama <code>dndecor</code> dan <code>createdeco</code>).<br>"
				+ "• Jika masalah berlanjut, <b>hapus sementara addon Create</b> satu per satu untuk mengidentifikasi yang bermasalah.<br>"
				+ "• Pastikan <b>Amendments dan Moonshine kompatibel</b> dengan versi Create dan Forge Anda.<br>"
				+ "• Periksa apakah ada versi beta atau fork terbaru dari addon yang bermasalah.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem nol dalam addon Create";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("Ditemukan mod yang tidak termasuk dalam platform aktif apa pun (Forge, Fabric, dll.):<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>Ini biasanya terjadi ketika:<br>")
				.append("• <b>Fabric dan Forge mod</b> dicampur dalam folder yang sama.<br>")
				.append("• Mod diinstal untuk versi Minecraft yang tidak kompatibel.<br>")
				.append("• Mod rusak atau bukan file JAR yang valid.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>Verifikasi bahwa semua mod untuk platform yang sama</b> (Forge <b>atau</b> Fabric, bukan keduanya).<br>"
				+ "• Gunakan <b>pohon mod</b> untuk mengidentifikasi platform mana yang mendeteksi setiap file.<br>"
				+ "• Hapus mod apa pun yang tidak Anda kenal atau yang dari platform berbeda.<br>"
				+ "• Jika Anda menggunakan launcher seperti CurseForge atau Prism, pastikan profil dikonfigurasi dengan benar.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Mod tidak kompatibel dengan loader aktif";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Tidak dapat membuat model <code>" + modid
				+ ":" + nombreModelo + "</code>.<br>" + "Ini menunjukkan bahwa mod <code>" + modid
				+ "</code> memiliki sumber daya yang rusak, hilang, "
				+ "atau tidak kompatibel dengan versi Minecraft Anda.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>Perbarui mod</b> ke versi terbaru yang kompatibel dengan instance Anda.<br>"
				+ "• Jika Anda menggunakan versi pengembangan atau khusus, kembali ke versi resmi.<br>"
				+ "• Verifikasi bahwa file JAR tidak rusak (instal ulang).<br>"
				+ "• Jika masalah berlanjut, laporkan kesalahan kepada pembuat mod termasuk log ini.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "Gagal membuat model sumber daya";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Konflik kritis terdeteksi antara mod <code>Moonlight</code> dan <code>Iceberg</code>.<br>"
				+ "Keduanya mencoba mendaftarkan sistem pemuatan ulang sumber daya dengan cara yang tidak kompatibel, "
				+ "yang menyebabkan kegagalan OpenGL karena tidak ada konteks grafis yang valid.<br>"
				+ "Masalah ini umum ketika menggunakan versi Forge yang menyertakan adapter mod Fabric.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>Perbarui kedua mod</b> ke versi terbaru yang kompatibel dengan versi Forge Anda.<br>"
				+ "• Jika masalah berlanjut, <b>hapus sementara Iceberg</b>, karena Moonlight biasanya merupakan dependensi yang lebih penting untuk mod lain.<br>"
				+ "• Pastikan Anda tidak memiliki versi duplikat atau tercampur dari Forge/Fabric mod ini.<br>"
				+ "• Periksa apakah mod lain (seperti Supplementaries, Citadel, dll.) sudah menyertakan fungsionalitas Iceberg secara internal.";
	}

	@Override
	public String instantanea() {
		// TODO: metode dihasilkan secara otomatis
		return "Snapshot";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		// TODO: metode dihasilkan secara otomatis
		return "Sejak Snapshot Terakhir";
	}

	@Override
	public String seleccionarUnArchivo() {
		// TODO: metode dihasilkan secara otomatis
		return "Pilih File";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		// TODO: metode dihasilkan secara otomatis
		return "Snapshot berhasil dibuat";
	}

	@Override
	public String errorCreandoInstantanea() {
		// TODO: metode dihasilkan secara otomatis
		return "Kesalahan saat membuat snapshot";
	}

	@Override
	public String consejo() {
		// TODO: metode dihasilkan secara otomatis
		return "Tip";
	}

	@Override
	public String resultadoMuestra() {
		// TODO: metode dihasilkan secara otomatis
		return "Dengantoh Hasil";
	}

	@Override
	public String historaDeModsDesc() {
		// TODO: metode dihasilkan secara otomatis
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>Tip:</b> Pilih dua file riwayat untuk membandingkan daftar mod. "
				+ "  Hasilnya menunjukkan <span style='color:%s;'>ditambahkan (+)</span> dan "
				+ "  <span style='color:%s;'>dihapus (&#8722;)</span> berdasarkan nama normal. "
				+ "  Gunakan tombol 'Snapshot' untuk membuat salinan file yang ada dengan ekstensi .snapshot."
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		// TODO: metode dihasilkan secara otomatis
		return "Dapatkan Tautan ke Log Sebagai Markdown tanpa Laporan";
	}

	@Override
	public String titulo_configuracion() {
		return "Pengaturan";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "Kesalahan tak terduga saat berbagi.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "Kesalahan tak terduga saat membuat tautan.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "Kesalahan tak terduga saat memproses tombol.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "Tidak ada file terkait untuk dibuka.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "File tidak ada:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "Tidak dapat membuka di editor.\nJalur akan disalin ke clipboard.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "Tidak dapat membuka file; jalur disalin ke papan klip.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Desktop tidak didukung; jalur disalin ke papan klip.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "Anda mengalami batas permintaan. Coba gunakan situs log lain atau API log lain.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		// TODO: metode dihasilkan secara otomatis
		return "Bagikan Tautan";
	}

	@Override
	public String infoDeTrazos() {
		// TODO: metode dihasilkan secara otomatis
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Memperbaiki bagian atas log adalah prioritas pertama. " + "Format adalah Level, Baris. "
				+ "Semua log memiliki sistem penomoran. " + Verificaciones.nl_html
				+ "Umumnya Anda perlu mencari di level yang lebih rendah di semua log; trace dengan level tinggi biasanya positif palsu. "
				+ "Penting untuk menggunakan kemampuan Anda untuk melihat di konsol, karena analisis trace tidak sempurna ketika ada banyak trace."
				+ "</b>";
	}

	// --- Buscador de Canario de Orden (Warrant Canary) ---

	/**
	 * Teks tombol untuk pencari canary urutan. Contoh: "Pencari canario de orden"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "Pencari Warrant Canary";
	}

	/**
	 * Pesan yang ditampilkan pada kotak dialog yang memberi tahu bahwa fungsi akan
	 * disponible próximamente.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "Fitur ini akan tersedia segera.";
	}

	/**
	 * Judul kotak dialog yang memberi tahu tentang ketersediaan fitur di masa
	 * mendatang buscador de canario de orden.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "Akan Datang";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		// TODO: metode dihasilkan secara otomatis
		return "Mod Tidak Kompatibel Dengan Crash Assistant (Salah)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		// TODO: metode dihasilkan secara otomatis
		return "Mod Tidak Kompatibel Dengan Paket Mod (Modpack) menggunakan CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		// TODO: metode dihasilkan secara otomatis
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant memiliki daftar mod yang mengatakan tidak kompatibel, tetapi kami tidak memiliki bukti bahwa mereka dan kesalahan hanya dalam bahasa Inggris. Jika Anda ingin bermain dengan mod ini, Anda dapat mengedit file <code>config/crash_assistant/config.toml</code> dan ubah <code>enabled = true</code> di bagian [compatibility] ke <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		// TODO: metode dihasilkan secara otomatis
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant memiliki kemampuan untuk menandai mod sebagai tidak kompatibel, tetapi kadang-kadang ini salah dan pesan kesalahan hanya tersedia dalam bahasa Inggris. Jika Anda ingin bermain dengan mod ini, Anda dapat mengedit file <code>config/crash_assistant/problematic_mods_config.json</code> dan ubah <code>should_crash_on_startup</code> dari <code>true</code> menjadi <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Kesalahan: Mod '" + modId
				+ "' memerlukan mod '" + dependencia + "'. Saat ini, " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Kesalahan: Mod '" + modId
				+ "' memerlukan versi '" + requerido + "' atau lebih tinggi dari '" + dependencia
				+ "', tetapi mod tidak diinstal." + "</span>";
	}

	// Di kelas MonitorDePID.idioma (tambahkan metode ini)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Kesalahan: Mod '" + modId
				+ "' tidak kompatibel dengan versi saat ini dari '" + dependencia + "'. "
				+ "Anda harus menghapus mod 'Iris/Oculus & GeckoLib Compat' karena tidak kompatibel dengan Superb Warfare dan tidak berfungsi dengan versi GeckoLib terbaru. "
				+ "Versi saat ini: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "Kesalahan: Tidak dapat menjalankan tugas untuk kelas '" + clase + "'. "
				+ "Kesalahan ini umum dengan mod yang tidak kompatibel satu sama lain atau memiliki konflik dengan mod lain yang diinstal.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "Kegagalan dalam Eksekusi Tugas";
	}

	public String recomendacion_fallos_ejecucion() {
		return "Jenis kesalahan ini biasanya terjadi ketika ada ketidaksesuaian antara mod. "
				+ "Khususnya umum dengan mod yang tidak berfungsi dengan benar dengan DengannectorMod.";
	}

	public String info_clase_problematica() {
		return "Kelas yang bermasalah:";
	}

	public String no_se_encontraron_clases_problema() {
		return "Tidak ditemukan kelas spesifik dengan masalah eksekusi.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Konflik kritis terdeteksi antara OptiFine dan Entity Model Features (EMF). "
				+ "Mod ini tidak kompatibel dan menyebabkan kegagalan injeksi yang mencegah game memulai." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "Konflik OptiFine dan Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "Copot OptiFine atau Entity Model Features, karena tidak kompatibel satu sama lain.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Konflik kritis terdeteksi antara OptiFine dan Fusion. "
				+ "Mod ini tidak kompatibel dan menyebabkan kegagalan injeksi yang mencegah game memulai." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "Konflik OptiFine dan Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "Copot OptiFine atau Fusion, karena tidak kompatibel satu sama lain.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel (diperlukan oleh Create) memerlukan Sodium 0.6.0-beta.2 atau lebih tinggi. Rubidium adalah 0.5.3. "
				+ "Pertimbangkan menggunakan <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> sebagai alternatif."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "Konflik Flywheel dan versi Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Perbarui Sodium ke 0.6.0-beta.2 atau lebih tinggi, atau instal <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> sebagai alternatif yang kompatibel.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Konflik kritis terdeteksi antara OptiFine dan Epic Fight. "
				+ "Mod ini tidak kompatibel dan menyebabkan kegagalan injeksi yang mencegah game memulai." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "Konflik OptiFine dan Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "Hapus OptiFine atau Epic Fight, karena tidak kompatibel satu sama lain.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Konflik kritis terdeteksi antara OptiFine dan Rubidium. "
				+ "Mod ini tidak kompatibel dan menyebabkan kegagalan injeksi yang mencegah game dimulai." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "Konflik OptiFine dan Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "Hapus OptiFine atau Rubidium, karena tidak kompatibel satu sama lain.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam mencoba memuat di server khusus, tetapi hanya kompatibel dengan klien. "
				+ "Hapus FreeCam dari server atau pastikan hanya diinstal di klien." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam di server khusus";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "Hapus FreeCam dari server khusus, karena hanya boleh diinstal di klien.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) mencoba memuat di server khusus, tetapi hanya kompatibel dengan klien. "
				+ "Hapus ETF dari server atau pastikan hanya diinstal di klien." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features di server khusus";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Hapus Entity Texture Features dari server khusus, karena hanya boleh diinstal di klien.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Anda harus menerima EULA Minecraft untuk menjalankan server. "
				+ "Edit file eula.txt dan ubah 'eula=false' menjadi 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "EULA Minecraft tidak diterima";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "Edit file eula.txt di folder server dan ubah 'eula=false' menjadi 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine mencoba memuat di server khusus, tetapi hanya kompatibel dengan klien. "
				+ "Hapus OptiFine dari server atau pastikan hanya diinstal di klien." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine di server khusus";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "Hapus OptiFine dari server khusus, karena hanya boleh diinstal di klien. Masalah ini juga biasanya disebabkan oleh instalasi versi Optifine untuk versi Minecraft yang salah atau konflik dengan mod lain dan Optifine.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks salah ditandai untuk 1.20.1 tetapi menggunakan metode 1.21.1. "
				+ "Mod mencoba menggunakan ResourceLocation.fromNamespaceAndPath, yang tidak ada di 1.20.1." + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Kesalahan versi di Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "Pastikan Anda menggunakan versi Iron's Spellbooks yang benar dan kompatibel dengan versi Minecraft Anda.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Konflik kritis terdeteksi antara OptiFine dan Embeddium. "
				+ "Mod ini tidak kompatibel dan menyebabkan kegagalan injeksi yang mencegah game dimulai." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "Konflik OptiFine dan Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "Hapus OptiFine atau Embeddium, karena tidak kompatibel satu sama lain.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Ini umum dengan mod pembuatan dunia yang konflik, terutama Terralith, AmplifiedNether, Nullscape, dan Incendium, serta mod pembuatan dunia lainnya. Mungkin Anda juga perlu menginstal mod yang hilang.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Dengantrollable mencoba memuat di server khusus, tetapi hanya kompatibel dengan klien. "
				+ "Hapus Dengantrollable dari server atau pastikan hanya diinstal di klien." + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Dengantrollable di server khusus";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Hapus Dengantrollable dari server khusus, karena hanya boleh diinstal di klien.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries menyebabkan kesalahan yang mencegah server dimulai. "
				+ "Mod ini memiliki masalah dengan pendaftaran perilaku api yang menyebabkan kegagalan saat memuat datapacks."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries mencegah server dimulai";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Coba perbarui Supplementaries ke versi terbaru atau nonaktifkan sementara untuk memungkinkan server dimulai.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) menemukan masalah dengan modul Jackson yang hilang. "
				+ "Beberapa mod seperti Valkyrien Skies dapat menyebabkan kesalahan ini dengan tidak menyertakan semua dependensi yang diperlukan."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Modul Jackson hilang di Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Hapus Groovy Modloader dan mod terkait seperti Valkyrien Skies yang dapat menyebabkan konflik dependensi.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat menemukan nama blok kayu yang tidak valid. "
				+ "Every Compat umumnya memiliki banyak masalah. Jangan gunakannya!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Nama tidak valid di Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Verifikasi paket sumber daya atau mod yang menggunakan Every Compat, karena mungkin memiliki nama blok yang tidak valid.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kode kesalahan terdeteksi (-1073741819) yang dapat disebabkan oleh overlay seperti GameCaster dari Razer, Discord, OBS Studio atau masalah driver NVIDIA."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "Kode kesalahan -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "Coba nonaktifkan overlay seperti GameCaster, Discord, atau OBS Studio, dan verifikasi driver NVIDIA Anda terbaru.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips memerlukan Immersive Messages sebagai dependensi tetapi tidak diinstal. "
				+ "Instal Immersive Messages agar Immersive Tooltips berfungsi dengan benar." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips tanpa dependensi";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Instal Immersive Messages, karena ini adalah dependensi yang diperlukan untuk Immersive Tooltips.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins memiliki masalah kompatibilitas dengan Apoli Mod di mana ItemStack tidak dapat di-cast ke EntityLinkedItemStack. "
				+ "Ini umum dalam versi di atas 6.6.0. Pertimbangkan menggunakan versi yang lebih lama atau beralih antara versi Fabric dan Forge."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Kesalahan casting di Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Gunakan Medieval Origins versi 6.6.0 atau lebih lama, atau coba beralih antara versi Fabric dan Forge dari mod.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether menyebabkan kesalahan dengan Registry Object yang tidak ada di MusicManager. "
				+ "Masalah ini terkait dengan mixin MusicManager dari Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "Kesalahan MusicManager di Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "Coba perbarui Reign of Nether atau hapus sementara untuk mengatasi kesalahan.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel hanya mendukung server YSM di Linux atau Android. "
				+ "Masalah ini telah diperbaiki dalam versi terbaru sejak 23 November 2025 di Modrinth." + "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel tidak kompatibel dengan Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "Perbarui YesSteveModel ke versi yang lebih baru dari Modrinth, karena masalah telah diperbaiki setelah 23 November.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Konflik kritis terdeteksi antara Moving Elevators dan OptiFine. "
				+ "Mod ini tidak kompatibel dan menyebabkan kegagalan injeksi yang mencegah game dimulai." + "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Konflik Moving Elevators dan OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Hapus OptiFine atau Moving Elevators, karena tidak kompatibel satu sama lain.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Konflik kritis terdeteksi antara Fabric API (fabric-resource-loader-v0) dan OptiFine. "
				+ "Mod ini tidak kompatibel dan menyebabkan kegagalan injeksi yang mencegah game dimulai." + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Konflik Fabric API dan OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "Hapus OptiFine atau perbarui Fabric API ke versi yang kompatibel.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod memiliki ITransformationService yang rusak yang tidak dapat diinstansiasi: " + claseProveedor
				+ ". " + "Mod ini harus dihapus untuk memungkinkan game dimulai." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService yang rusak";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "Hapus mod yang berisi kelas " + claseProveedor + ", karena memiliki ITransformationService yang rusak.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Mod memiliki spesifikasi versi yang tidak valid. "
				+ "Versi harus dikelilingi oleh tanda kurung siku. "
				+ "Anda dapat menggunakan utilitas grep/greprf dari sidebar dengan mencari versi </span>" + version
				+ "<span style='color:#" + config.obtenerColorError()
				+ "'> untuk mengidentifikasi mod mana yang memiliki masalah.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Versi tidak valid di mod";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "Gunakan utilitas grep/greprf dari sidebar untuk mencari versi yang bermasalah dan temukan mod yang memuatnya.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kesalahan stack smashing terdeteksi yang menghentikan proses. "
				+ "Ini dapat disebabkan oleh masalah dengan Early Window di Forge/NeoForge/PillowMC atau dengan LWJGL 3.2.2 dan lebih baru."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Stack Smashing Terdeteksi";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Periksa pengaturan Early Window dan pertimbangkan menggunakan versi LWJGL yang berbeda atau tinjau mod terkait dengan jendela awal.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore hanya untuk modpack tertentu dan tidak boleh digunakan dalam instalasi umum, karena menyebabkan masalah."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore dengan versi Java yang tidak kompatibel";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "Hapus GregTechEasyCore, karena hanya untuk modpack tertentu dan tidak kompatibel dengan instalasi umum Anda.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas Minecraft yang hilang. Kemungkinan penyebabnya:</b>" + "<ul>"
				+ "<li>Anda memiliki mod untuk versi game lain. Anda dapat menggunakan <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> untuk memeriksa apakah kelas tersebut ada pada versi Anda.</li>"
				+ "<li>Anda memiliki instalasi Minecraft yang rusak (umum terjadi pada CurseForge App, ModrinthApp/Theseus/Astralrinth, dan launcher modpack lainnya). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Lihat tutorial</a> untuk mengatasi masalah dengan CurseForge.</li>"
				+ "<li>Anda memiliki coremod yang rusak (jika sebuah coremod gagal, hal itu dapat menghalangi pemuatan kelas).</li>"
				+ "</ul>"
				+ "<p>Catatan: Anda dapat menggunakan alat <b>grepr/fgrepr</b> di panel samping untuk menemukan mod yang merujuk ke kelas-kelas yang hilang, selama Anda menggunakan '/' pada namanya.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas DangerZone yang hilang. Kemungkinan penyebabnya:</b>" + "<ul>"
				+ "<li>Anda memiliki mod untuk versi game lain.</li>" + "<li>Anda memiliki coremods yang rusak.</li>"
				+ "<li>Anda memiliki launcher atau instalasi yang rusak.</li>" + "</ul>"
				+ "<p>Catatan: Anda dapat menggunakan alat <b>grepr/fgrepr</b> di sidebar untuk menemukan mod yang mereferensikan kelas yang hilang, selama Anda menggunakan '/' dalam nama.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas yang hilang dari FeatureCreep. Alasan yang mungkin:</b>" + "<ul>"
				+ "<li>Anda memiliki mod untuk versi FeatureCreep lain (mis: ESR vs Nightly atau v4 vs v12).</li>"
				+ "<li>Anda dapat menginstal FeatureCreep dari CurseForge atau MinecraftStorage.</li>" + "</ul>"
				+ "<p>Catatan: Anda dapat menggunakan alat <b>grepr/fgrepr</b> di sidebar untuk menemukan mod yang mereferensikan kelas yang hilang, selama Anda menggunakan '/' dalam nama.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas yang hilang dari ModLauncher. Alasan yang mungkin:</b>" + "<ul>"
				+ "<li>Mod Anda adalah untuk build ModLauncher MinecraftForge, PillowMC, atau NeoForge yang berbeda (ModLauncher digunakan dengan loader ini).</li>"
				+ "<li>Ada banyak update modloaders untuk versi Minecraft.</li>"
				+ "<li>Anda memiliki instalasi launcher yang rusak (umum dengan CurseForge App, ModrinthApp/Theseus/Astralrinth dan launcher modpack lainnya). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Lihat tutorial</a> untuk mengatasi masalah CurseForge.</li>"
				+ "</ul>"
				+ "<p>Catatan: Anda dapat menggunakan alat <b>grepr/fgrepr</b> di sidebar untuk menemukan mod yang mereferensikan kelas yang hilang, selama Anda menggunakan '/' dalam nama.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas yang hilang dari Minecraft Forge. Alasan yang mungkin:</b>" + "<ul>"
				+ "<li>Mod Anda adalah untuk build MinecraftForge yang berbeda.</li>"
				+ "<li>Ada banyak update modloaders untuk versi Minecraft.</li>"
				+ "<li>Anda memiliki instalasi yang rusak (umum dengan CurseForge App, ModrinthApp/Theseus/Astralrinth dan launcher modpack lainnya). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Lihat tutorial</a> untuk mengatasi masalah CurseForge.</li>"
				+ "</ul>"
				+ "<p>Catatan: Anda dapat menggunakan alat <b>grepr/fgrepr</b> di sidebar untuk menemukan mod yang mereferensikan kelas yang hilang, selama Anda menggunakan '/' dalam nama.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas yang hilang dari NeoForge. Alasan yang mungkin:</b>" + "<ul>"
				+ "<li>Mod Anda adalah untuk build NeoForge yang berbeda.</li>"
				+ "<li>Ada banyak update modloaders untuk versi Minecraft.</li>"
				+ "<li>Anda memiliki instalasi yang rusak (umum dengan CurseForge App, ModrinthApp/Theseus/Astralrinth dan launcher modpack lainnya). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Lihat tutorial</a> untuk mengatasi masalah CurseForge.</li>"
				+ "</ul>"
				+ "<p>Catatan: Anda dapat menggunakan alat <b>grepr/fgrepr</b> di sidebar untuk menemukan mod yang mereferensikan kelas yang hilang, selama Anda menggunakan '/' dalam nama.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas yang hilang dari Fabric Loader. Alasan yang mungkin:</b>" + "<ul>"
				+ "<li>Mod Anda adalah untuk build Fabric Loader yang berbeda.</li>"
				+ "<li>Ada banyak update modloaders untuk versi Minecraft.</li>"
				+ "<li>Anda memiliki instalasi yang rusak (umum dengan CurseForge App, ModrinthApp/Theseus/Astralrinth dan launcher modpack lainnya). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Lihat tutorial</a> untuk mengatasi masalah CurseForge.</li>"
				+ "<li>Banyak mod memerlukan Fabric API. Silakan instal Fabric API jika diperlukan.</li>" + "</ul>"
				+ "<p>Catatan: Anda dapat menggunakan alat <b>grepr/fgrepr</b> di sidebar untuk menemukan mod yang mereferensikan kelas yang hilang, selama Anda menggunakan '/' dalam nama.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Anda memiliki kelas yang hilang dari PillowMC. Alasan yang mungkin:</b>" + "<ul>"
				+ "<li>Mod Anda adalah untuk build PillowMC yang berbeda.</li>"
				+ "<li>Ada banyak update modloaders untuk versi Minecraft.</li>"
				+ "<li>Anda memiliki instalasi yang rusak (umum dengan CurseForge App, ModrinthApp/Theseus/Astralrinth dan launcher modpack lainnya). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Lihat tutorial</a> untuk mengatasi masalah CurseForge.</li>"
				+ "</ul>"
				+ "<p>Catatan: Anda dapat menggunakan alat <b>grepr/fgrepr</b> di sidebar untuk menemukan mod yang mereferensikan kelas yang hilang, selama Anda menggunakan '/' dalam nama.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Anda memiliki mod yang secara sengaja menyebabkan lag. Uranium adalah mod lag. Tidak selalu menyebabkan crash, tetapi pada akhirnya dapat melakukannya."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack ditandai sebagai kompatibel dengan 1.19.* tetapi untuk 1.20.*, yang menyebabkan kesalahan kelas tidak ditemukan. "
				+ "Mod mencoba menggunakan DamageSources yang tidak ada di versi Minecraft saat ini." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Kesalahan versi di Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Pastikan Anda menggunakan versi Falling Attack yang benar dan kompatibel dengan versi Minecraft Anda.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>")
				.append("Anda perlu menginstal CFR (Class File Reader) untuk menggunakan fungsi ini.<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append(
					"Pada sistem Linux, NetBSD, atau FreeBSD, Anda dapat menginstal CFR dari pengelola paket Anda.<br>")
					.append("Cari paket di: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append(
				"Alternatif, Anda dapat mengunduh versi yang dimodifikasi yang digunakan oleh FabricMC dari:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("Simpan dalam folder berikut:<br>").append("<b>")
				.append(new java.io.File(com.asbestosstar.crashdetector.Statics.carpeta_mundial_como_archivo, "cfr/")
						.getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>Penting:</b> setelah menginstal CFR, Anda harus restart mod agar mengenalinya dengan benar.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "Tidak ada potret yang tersedia";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "Tidak dapat menemukan kelas: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "Decompiler CFR – Sakura Riddle (Tidak Resmi)";
	}

	@Override
	public String cfrClaseActual() {
		return "Kelas saat ini";
	}

	@Override
	public String botonDescargarCfr() {
		return "Unduh CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "Buka folder instalasi";
	}

	@Override
	public String colorFondoPrincipal() {
		return "Warna latar belakang utama";
	}

	@Override
	public String colorTextoBotonReset() {
		return "Warna teks tombol reset";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "Warna teks kolom pencarian";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "Warna teks menu dropdown filter";
	}

	@Override
	public String colorTextoRenderer() {
		return "Warna teks renderer";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "Warna teks overlay loading";
	}

	@Override
	public String colorBorde() {
		return "Warna perbatasan";
	}

	@Override
	public String colorFondoRetrato() {
		return "Warna latar belakang mode potret";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "Warna tautan untuk berbagi";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "Warna latar belakang bidang berbagi";
	}

	@Override
	public String rosaFondo() {
		return "Merah muda latar belakang";
	}

	@Override
	public String rosaSuave() {
		return "Merah muda lembut";
	}

	@Override
	public String moradoAcento() {
		return "Ungu aksen";
	}

	@Override
	public String textoOscuro() {
		return "Teks gelap";
	}

	@Override
	public String bordeSuave() {
		return "Perbatasan lembut";
	}

	@Override
	public String fondoCampo() {
		return "Latar belakang bidang";
	}

	@Override
	public String fondoVistaPrevia() {
		return "Latar belakang pratinjau";
	}

	@Override
	public String sintaxisConstructor() {
		return "Warna sintaks: konstruktor";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "Warna sintaks: pesan bantuan";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "Warna sintaks: tag HTML";
	}

	@Override
	public String colorFondoVentana() {
		return "Warna latar belakang jendela";
	}

	@Override
	public String colorPanel() {
		return "Warna panel";
	}

	@Override
	public String colorBotonTexto() {
		return "Warna teks tombol";
	}

	@Override
	public String colorCampo() {
		return "Warna bidang";
	}

	@Override
	public String colorBordeDestacado() {
		return "Warna perbatasan sorotan";
	}

	@Override
	public String colorSeleccionTexto() {
		return "Warna latar belakang seleksi teks";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "Warna teks yang dipilih";
	}

	@Override
	public String colorBeigeListas() {
		return "Beige daftar";
	}

	@Override
	public String colorBordeListas() {
		return "Warna tepi daftar";
	}

	@Override
	public String colorPila() {
		return "Warna stack trace";
	}

	@Override
	public String colorFondoResultados() {
		return "Warna latar belakang hasil";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "Jika Anda benar-benar membutuhkannya, pastikan Anda menggunakan versi yang kompatibel dengan sistem operasi dan versi Minecraft Anda.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "Gunakan Iris atau Oculus tanpa OptiFine untuk shader modern.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kesalahan: kunci pendaftaran tidak sah dengan karakter yang tidak diperbolehkan.</b>" + "<ul>"
				+ "<li><b>Kunci terdeteksi:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>Di Minecraft, semua kunci registri (tag, resep, pencapaian, dan sebagainya) harus ditulis dalam <b>huruf kecil</b> dan hanya menggunakan huruf, angka, garis bawah, tanda hubung, dan garis miring.</li>"
				+ "<li>Kesalahan ini biasanya disebabkan oleh mod yang dikodekan dengan buruk atau datapack yang cacat.</li>"
				+ "</ul>"
				+ "<p><b>Sangat penting:</b> Gunakan alat <b>grep</b> atau <b>fgrep</b> di bilah sisi dan aktifkan opsi <b>\"Cari dalam file JAR\"</b> untuk menemukan mod mana yang berisi kunci yang salah ini.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Kunci registri dengan huruf besar atau karakter tidak valid";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "Gunakan 'grepr' atau 'fgrepr' dengan \"Cari dalam file JAR\" untuk menemukan mod penyebabnya.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "Jika Anda tidak dapat mengidentifikasi mod tersebut, hapus mod yang baru dipasang, terutama yang menambahkan blok, item, atau alat.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Kesalahan saat memuat mod <b>"
				+ escapeHtml(modNombre) + "</b>.</b>" + "<ul>"
				+ "<li>Mod gagal menginisialisasi salah satu komponennya (mis: menu konfigurasi).</li>"
				+ "<li>Ini biasanya terjadi karena ketidaksesuaian dengan versi Minecraft, Fabric, atau mod lain.</li>"
				+ "</ul>" + "<p>Jika kesalahan berlanjut, hapus atau perbarui mod <b>" + escapeHtml(modNombre)
				+ "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "Kesalahan inisialisasi mod (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "Hapus mod '" + modNombre + "' dari folder 'mods'.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "Perbarui mod '" + modNombre + "' ke versi yang kompatibel dengan instalasi Anda.";
	}

	@Override
	public String nombre_error_en_garde() {
		return "Kesalahan di mod En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "Pastikan Anda menggunakan versi En Garde! yang kompatibel dengan versi Minecraft dan loader (Fabric/Forge) Anda.";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "Jika Anda menggunakan mod pertarungan lain (Epic Fight, Caelus, dll.), nonaktifkan atau hapus En Garde! untuk menghindari konflik.";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "Kesalahan di IdleTweaks (saluran jaringan tidak diketahui)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "Perbarui IdleTweaks ke versi terbaru yang kompatibel dengan Minecraft Anda.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "Hapus IdleTweaks dari folder 'mods' jika Anda tidak membutuhkannya.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kesalahan autentikasi (HTTP 401) terdeteksi saat mencoba masuk ke Minecraft.</b>"
				+ "<p>Kesalahan ini <b>jarang menjadi penyebab langsung crash</b>, tetapi menunjukkan bahwa Anda menggunakan akun yang tidak diautentikasi (bajakan).</p>"
				+ "<p>Saluran dukungan resmi (proyek perusahaan, VTubers, pembuat modpack, dll.) <b>tidak dapat membantu</b> jika Anda menggunakan salinan bajakan, "
				+ "karena pembatasan peraturan obrolan mereka, kontrak, kesepakatan dengan Mojang/Microsoft, atau kebijakan reputasi.</p>"
				+ "<p>Verifikasi ini dapat <b>dinonaktifkan dalam konfigurasi perusahaan</b> detektor. "
				+ "Peringatan: deteksi anti-pembajakan <b>tidak sempurna</b> dan dapat diaktifkan dalam lingkungan pengembangan, dengan internet yang tidak stabil, atau saat menggunakan launcher yang dimodifikasi.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>Hak Miranda jika Anda mencoba bergabung dengan dukungan bagaimanapun:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft Bajakan";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "Nonaktifkan verifikasi anti-pembajakan";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Anda menggunakan peluncur <code>" + id
				+ "</code>, yang <b>tidak termasuk dalam daftar peluncur yang direkomendasikan</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "Untuk pengalaman yang lebih baik, gunakan salah satu launcher yang direkomendasikan berikut ini:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Launcher tidak direkomendasikan";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "Beralihlah ke launcher dari daftar yang direkomendasikan.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Anda menggunakan <b>peluncur yang tidak disarankan</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "Sangat disarankan untuk menggunakan salah satu launcher berikut:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Lanzador desaconsejado";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "Beralihlah ke launcher yang direkomendasikan untuk mendapatkan dukungan.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod yang direkomendasikan untuk lingkungan ini belum terpasang.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "Mod yang direkomendasikan belum ada";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "Instal mod yang direkomendasikan untuk pengalaman terbaik.";
	}

	// --- TienesModDesAnimado ---

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "Hapus mod yang tidak disarankan untuk menghindari masalah.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Manipulasi tidak sah terdeteksi di file kritis. Anda telah memodifikasi file atau menggunakan launcher yang tidak terpercaya.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "Manipulasi Terdeteksi";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "Instal ulang file asli untuk memulihkan integritas.";
	}

	@Override
	public String configuracionCorporativa() {
		return "Konfigurasi Perusahaan";
	}

	@Override
	public String idiomaRespaldo() {
		return "Bahasa Cadangan";
	}

	@Override
	public String buscardorHabilitado() {
		return "Aktifkan Finder";
	}

	@Override
	public String nombreHerramienta() {
		return "Nama Alat";
	}

	@Override
	public String condenarPirateria() {
		return "Kecam Pembajakan";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Launcher yang Direkomendasikan";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Launcher yang Tidak Disarankan";
	}

	@Override
	public String modsRecomendados() {
		return "Mod yang Direkomendasikan";
	}

	@Override
	public String modsDesaconsejados() {
		return "Mod yang Tidak Disarankan";
	}

	@Override
	public String antiTamper() {
		return "AntiTamper";
	}

	@Override
	public String proximamente() {
		return "Akan Datang";
	}

	@Override
	public String informacion() {
		return "Informasi";
	}

	@Override
	public String errorCargandoImagen() {
		return "Kesalahan saat memuat gambar";
	}

	@Override
	public String configuracionBasica() {
		return "Konfigurasi Dasar";
	}

	@Override
	public String funcionalidades() {
		return "Fitur";
	}

	@Override
	public String derechosMiranda() {
		return "Hak Miranda (SANGAT disarankan)";
	}

	@Override
	public String gestionVerificaciones() {
		return "Pengelolaan Verifikasi";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "Nama";
	}

	@Override
	public String codigoVerificacion() {
		return "Kode";
	}

	@Override
	public String documentacionVerificacion() {
		return "Dokumentasi";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "Verifikasi Diaktifkan:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "Verifikasi Dinonaktifkan:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "Nonaktifkan semua non-korporat";
	}

	@Override
	public String verCodigo() {
		return "Lihat Kode";
	}

	@Override
	public String verDocumentacion() {
		return "Lihat Dokumentasi";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "Pilih verifikasi untuk dinonaktifkan.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "Pilih verifikasi untuk diaktifkan.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "Verifikasi %d tidak direkomendasikan untuk penggunaan korporat telah dinonaktifkan.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "Tidak ada verifikasi non-korporat untuk dinonaktifkan.";
	}

	@Override
	public String operacionCompletada() {
		return "Operasi selesai";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "Kami merindukanmu Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "Warna Verifikasi Korporat";
	}

	// Metode untuk manajemen launcher
	@Override
	public String nombreLanzador() {
		return "Nama Launcher";
	}

	@Override
	public String motivo() {
		return "Alasan";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Launcher Tidak Disarankan";
	}

	@Override
	public String moverADesaconsejados() {
		return "Jangan Rekomendasikan";
	}

	@Override
	public String moverARecomendados() {
		return "Rekomendasikan";
	}

	@Override
	public String guardarCambios() {
		return "Simpan Perubahan";
	}

	@Override
	public String cancelar() {
		return "Batal";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "Silakan pilih launcher untuk dipindahkan.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "Perubahan telah disimpan dengan berhasil!";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "Launcher ini tidak direkomendasikan karena masalah keamanan dan stabilitas yang diketahui.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEn(String nombreLanzador) {
		return "Launcher ini tidak direkomendasikan karena masalah keamanan dan stabilitas yang diketahui.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoPt(String nombreLanzador) {
		return "Launcher ini tidak direkomendasikan karena masalah keamanan dan stabilitas yang diketahui.";
	}

	@Override
	public String razones() {
		return "Alasan";
	}

	@Override
	public String agregarLanzador() {
		return "Tambah Launcher";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "Pilih launcher yang akan dihapus.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "Pilih launcher yang akan diedit.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "Edit alasan untuk " + idLanzador;
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "Pilih bahasa";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "Launcher ini adalah yang disarankan " + Statics.nombre_cd.obtener() + " sebagai pilihan yang baik.";
	}

	public String modsNoRecomendados() {
		return "Mod yang tidak disarankan";
	}

	public String agregarMod() {
		return "Tambah mod";
	}

	public String quitarMod() {
		return "Hapus mod";
	}

	public String modId() {
		return "Mod ID/Nama JBoss Modules";
	}

	public String rutaMod() {
		return "Jalur / berkas mod";
	}

	public String errorDebeIndicarMod() {
		return "Anda harus menentukan setidaknya modid atau path mod.";
	}

	public String modsNoRecomendadosAviso() {
		return "Di sini Anda dapat mendaftarkan mod yang tidak disarankan agar " + Statics.nombre_cd.obtener()
				+ " mendeteksinya jika terpasang.";
	}

	@Override
	public String anularNormal() {
		// TODO: metode dihasilkan secara otomatis
		return "Batalkan Normal";
	}

	@Override
	public String anularNormalDescripcion() {
		// TODO: metode dihasilkan secara otomatis
		return Statics.nombre_cd.obtener() + " harus memberi peringatan meskipun tidak crash";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "Daftarkan mod yang direkomendasikan " + Statics.nombre_cd.obtener() + ". Jika belum ada, "
				+ Statics.nombre_cd.obtener() + " dapat memberi peringatan.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "Jika Anda memutuskan untuk mengaktifkan peringatan antipirat, disarankan untuk menentukan di sini "
				+ "hak-hak orang yang meminta dukungan, sebagai langkah pencegahan.\n\n"

				+ "Bertentangan dengan kepercayaan umum, banyak komunitas dan saluran dukungan populer "
				+ "TIDAK memerlukan aktivasi peringatan antipirat untuk memberikan bantuan. Namun, "
				+ "mendokumentasikan hak-hak ini dapat berguna jika seseorang mengakses saluran "
				+ "dukungan apa pun.\n\n"

				+ "Anda dapat mengdasarkan ini pada dokumen resmi seperti Kartu Hak Dasar Tawanan " + "di Meksiko:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "Serta pada prinsip hukum yang sebanding yang digunakan di negara-negara lain, termasuk "
				+ "Amerika Serikat, Federasi Rusia, Republik Rakyat Tiongkok, Republik Islam Iran "
				+ "dan Republik Rakyat Demokratik Korea Utara.\n\n"

				+ "Beberapa contoh hak yang dapat disertakan:\n"
				+ "• Hak untuk tidak memberikan informasi yang tidak perlu untuk dukungan, seperti launcher yang digunakan, "
				+ "nama pengguna atau UUID.\n" + "• Hak untuk tidak menyalahkan diri sendiri.\n"
				+ "• Hak untuk menolak menjawab pertanyaan yang tidak perlu untuk menyelesaikan masalah.\n"
				+ "• Hak untuk menerima bimbingan dalam obrolan.\n"
				+ "• Hak untuk menggunakan anonimisasi log bawaan di " + Statics.nombre_cd.obtener() + ".\n\n"

				+ "Teks ini menerima konten HTML.";
	}

	@Override
	public String quitar() {
		return "Hapus";
	}

	@Override
	public String rutaArchivo() {
		return "Jalur file";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "Jalur yang dipilih berada di luar direktori game saat ini. "
				+ "Hanya file dan folder dalam direktori saat ini atau subdirektorinya yang diizinkan.";
	}

	@Override
	public String mensajeDeSylentBell() {
		// TODO: metode dihasilkan secara otomatis
		return "<html><div style='width:150px; text-align:center;'>"
				+ "Pendapat dan komentar Sylent Bell tidak harus sejalan dengan milik kami; "
				+ "kami hanya berpikir akan lucu memasukkannya di sini. " + Statics.nombre_cd.obtener()
				+ " adalah sekuler." + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod GML (Groovy ModLoader) memerlukan perubahan ini dan merupakan sumber paling umum dari masalah ini.</b>";
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
				+ "Penggunaan <i>Independent Flywheel</i> telah terdeteksi.</b>"
				+ "<p><b>Independent Flywheel sudah usang (deprecated)</b> dan tidak boleh digunakan di versi modern.</p>"
				+ "<p>Versi saat ini dari <b>Create</b> <b>sudah menyertakan Flywheel</b>, jadi menginstalnya secara independen "
				+ "menyebabkan konflik kompatibilitas dan kesalahan loading.</p>"
				+ "<p>Beberapa mod yang secara eksplisit bergantung pada Independent Flywheel mungkin "
				+ "<b>tidak berfungsi</b> atau <b>berfungsi dengan tidak stabil</b>. "
				+ "Dalam kasus lanjutan tertentu, mod ini mungkin dapat berfungsi jika "
				+ "<b>mengedit file <code>mods.toml</code> secara manual</b> untuk menyesuaikan rentang versi, "
				+ "meskipun ini <b>tidak disarankan</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Mod terdeteksi yang mereferensikan Flywheel:</b></p>" + "<ul>" + listaMods.toString()
								+ "</ul>")
				+ "<p>Solusi yang disarankan adalah <b>menghapus Independent Flywheel</b> dan hanya menggunakan "
				+ "versi yang disertakan dengan Create.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Flywheel Independen";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan terkait dengan mod <i>Floral Enchantments</i> telah terdeteksi.</b>"
				+ "<p>Crash disebabkan oleh kegagalan internal mod saat menangani data game, "
				+ "yang menyebabkan <b>NullPointerException</b> selama eksekusi.</p>"
				+ "<p>Masalah ini biasanya dapat diselesaikan dengan memperbarui mod atau menghapusnya .</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Kesalahan Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Anda memiliki versi NeoForge dari MixinExtras dan versi normal. Jika Anda menggunakan MinecraftForge, Anda dapat menginstal <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>tautan ini</a> untuk solusinya.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan dalam shadow terrain dengan shader (Iris) telah terdeteksi.</b>"
				+ "<p>Masalah terjadi selama renderisasi terrain.</p>"
				+ "<p>Disarankan untuk <b>mencoba game tanpa shader</b> atau mengurangi kualitas grafis, "
				+ "terutama dalam pengaturan <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "Terrain Shadow (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Tick server yang berlebihan lama telah terdeteksi.</b>"
				+ "<p>Ini menunjukkan bahwa game telah terkunci terlalu lama dalam satu tick.</p>"
				+ "<p>Disarankan untuk <b>meninjau thread dump</b> yang dihasilkan dalam log untuk mengidentifikasi penyebabnya.</p>"
				+ "<p><b>Analisis Stack Trace</b> dapat membantu Anda menemukan asal kemacetan.</p>"
				+ "<p>Selain itu, tombol <b>Lihat di log</b> akan menyoroti dalam merah mod yang mungkin bertanggung jawab, "
				+ "serta entri yang dikelilingi oleh <code>$modid$</code>, yang biasanya menunjukkan asal masalahnya. Untuk sampling real-time, kami merekomendasikan menggunakan sampler CPU di VisualVM. Pastikan server atau komputer Anda cukup kuat untuk menangani mod yang Anda gunakan, karena semua mod Anda mungkin berfungsi dengan baik, tetapi Anda mungkin memiliki terlalu banyak.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Tick Server Panjang";
	}

	@Override
	public String tituloLFPDPPP() {
		return "UNDANG-UNDANG FEDERAL PERLINDUNGAN DATA PRIBADI DALAM KEKUASAAN INDIVIDU";
	}

	@Override
	public String aceptarPermanentemente() {
		return "Terima secara permanen";
	}

	// Metode untuk Undang-Undang Perlindungan Bahasa Budaya Pyongyang
	public String actaProteccionIdiomaCultural() {
		return "Undang-Undang Perlindungan Bahasa Budaya Pyongyang";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "Terjemahan Korea mengandung istilah slang Selatan yang harus dihindari untuk mematuhi hukum. "
				+ "Penggunaan bahasa asing, terutama yang berasal dari Selatan, secara ketat dilarang "
				+ "menurut Undang-Undang Perlindungan Bahasa Budaya Pyongyang.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "Untuk informasi lebih lanjut, silakan lihat dokumen resmi undang-undang: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>Undang-Undang Perlindungan Bahasa Budaya Pyongyang</a>";
	}

	public String leerLeyCompleta() {
		return "Baca Undang-Undang Lengkap";
	}

	public String errorAbriendoEnlace() {
		return "Kesalahan membuka tautan";
	}

	@Override
	public String canarioTitulo() {
		return "Warrant Canary";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — Monitor Pengawasan";
	}

	@Override
	public String revisar() {
		return "Tinjau";
	}

	@Override
	public String cerrar() {
		return "Tutup";
	}

	@Override
	public String canarioTodoSeguro() {
		return "Semua layanan melaporkan status aman.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "ALERT: " + c + " layanan melaporkan status tidak aman.";
	}

	@Override
	public String colorAlerta() {
		return "Warna peringatan";
	}

	public String opcionesMunidiales() {
		return "Opsi Global";
	}

	public String consentimientoLFPDPPP() {
		return "Persetujuan LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "Aktifkan transfer token akses di Handoff untuk ReLauncher (tidak disarankan).";
	}

	public String consolaDesarrollo() {
		return "Konsol Pengembangan";
	}

	public String mundial() {
		return "Global";
	}

	public String ningun() {
		return "Tidak ada";
	}

	// Konsol pengembang
	public String consolaDesarrollador() {
		return "Konsol Pengembang";
	}

	public String bajar() {
		return "Turunkan";
	}

	public String logsSoporte() {
		return "Log untuk dukungan";
	}

	public String detenerProceso() {
		return "Hentikan proses";
	}

	// Menu konteks
	public String copiarSeleccion() {
		return "Salin pilihan";
	}

	public String seleccionarTodo() {
		return "Pilih semua";
	}

	public String copiarTodo() {
		return "Salin semua";
	}

	public String guardarTodoComoArchivo() {
		return "Simpan semua sebagai file";
	}

	public String obtenerEnlaceSoporte() {
		return "Dapatkan tautan dukungan";
	}

	public String borrarTodo() {
		return "Hapus semua";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "Warna latar belakang konsol";
	}

	public String colorTextoConsola() {
		return "Warna teks konsol";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "Persetujuan dikonfirmasi.\nIntegrasi log sharing akan diimplementasikan di sini.";
	}

	@Override
	public String usarSakuraOriginal() {
		// TODO: metode dihasilkan secara otomatis
		return "Gunakan Gambar Sakura Riddle Asli";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "\"Warrant canary\" adalah mekanisme transparansi.\n\n"
				+ "Selama pesan ini ada dan layanan muncul aman, "
				+ "itu berarti proyek TIDAK menerima perintah pengadilan rahasia, "
				+ "permintaan sensor, atau permintaan hukum untuk pengawasan.\n\n"
				+ "Jika canary apa pun menghilang atau menandai tidak aman, "
				+ "itu menunjukkan bahwa sesuatu telah berubah secara hukum.\n\n"
				+ "Panel ini memeriksa semua canary yang terdaftar dalam sistem dan menampilkan "
				+ "status mereka saat ini.\n\n" + "Tekan \"Tinjau\" untuk memperbarui status.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		// TODO: metode dihasilkan secara otomatis
		return "Atur ulang semua opsi ke nilai default mereka?";
	}

	@Override
	public String gui() {
		// TODO: metode dihasilkan secara otomatis
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		// TODO: metode dihasilkan secara otomatis
		return "Tanpa Opsi";
	}

	@Override
	public String seleccionaColor() {
		// TODO: metode dihasilkan secara otomatis
		return "Pilih warna";
	}

	@Override
	public String botonMostrarGUI() {
		return "Tampilkan GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "Simpan semua";
	}

	@Override
	public String botonRestablecerTodo() {
		return "Atur Ulang Semua";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms tidak dimuat";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan terdeteksi saat mengakses API LuckPerms.</b>"
				+ "<p>Pesan menunjukkan bahwa <b>LuckPerms tidak dimuat</b> pada saat plugin lain mencoba menggunakannya.</p>"
				+ "<p><b>Kemungkinan penyebab:</b></p>" + "<ul>"
				+ "<li>Plugin <b>LuckPerms tidak diinstal</b> atau <b>gagal untuk dimulai</b>.</li>"
				+ "<li>Plugin lain mencoba mengakses LuckPerms terlalu cepat atau dengan cara yang salah.</li>"
				+ "</ul>" + "<p>Disarankan untuk <b>meninjau konsol</b> menggunakan tautan untuk mengidentifikasi "
				+ "plugin yang memanggil LuckPerms dan memverifikasi kompatibilitasnya.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Shaderpack Iris tidak dimuat";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "tidak diketahui" : shaderpack;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan terdeteksi saat memuat shaderpack dengan Iris/Oculus.</b>"
				+ "<p><b>Shaderpack yang terpengaruh:</b> " + nombre + "</p>"
				+ "<p>Minecraft tidak dapat membuka file shaderpack (FileSystemNotFoundException).</p>"
				+ "<p><b>Solusi yang mungkin:</b></p>" + "<ul>"
				+ "<li>Verifikasi bahwa shaderpack diinstal dengan benar di folder <b>shaderpacks</b>.</li>"
				+ "<li>Unduh ulang shaderpack, karena file mungkin rusak.</li>"
				+ "<li>Jika masalah berlanjut, hapus file <b>config/iris.properties</b> untuk memulai ulang konfigurasi Iris.</li>"
				+ "</ul>" + "<p>Setelah menerapkan perubahan, mulai game lagi.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "Tidak dapat menulis file konfigurasi";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "tidak diketahui" : ruta;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Terjadi kesalahan saat menyimpan file konfigurasi.</b>" + "<p><b>File yang terpengaruh:</b> "
				+ archivo + "</p>"
				+ "<p>Minecraft tidak dapat menulis file menggunakan penulisan atom (REPLACE_ATOMIC).</p>"
				+ "<p><b>Ini biasanya terjadi karena:</b></p>" + "<ul>"
				+ "<li>Izin tidak benar di folder atau file.</li>" + "<li>File ditandai sebagai baca-saja.</li>"
				+ "<li>Program lain (antivirus, backup, editor) memblokir file.</li>" + "</ul>"
				+ "<p><b>Rekomendasi:</b></p>" + "<ul>"
				+ "<li>Verifikasi bahwa Anda memiliki izin tulis di folder.</li>"
				+ "<li>Hapus atribut baca-saja dari file.</li>"
				+ "<li>Tutup program yang mungkin menggunakan file tersebut.</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "Akses ditolak saat membuat backup";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "tidak diketahui" : origen;
		String dst = backup == null || backup.isEmpty() ? "tidak diketahui" : backup;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Terjadi kesalahan izin saat membuat backup file konfigurasi.</b>" + "<p><b>File asli:</b> " + src
				+ "</p>" + "<p><b>File backup:</b> " + dst + "</p>"
				+ "<p>Sistem operasi telah memblokir akses selama penyimpanan file.</p>"
				+ "<p><b>Ini biasanya terjadi karena:</b></p>" + "<ul>" + "<li>Izin tidak cukup di folder.</li>"
				+ "<li>File ditandai sebagai baca-saja.</li>"
				+ "<li>Program lain (antivirus, sinkronisasi, editor) menggunakan file.</li>" + "</ul>"
				+ "<p><b>Rekomendasi:</b></p>" + "<ul>" + "<li>Verifikasi izin folder <b>config</b>.</li>"
				+ "<li>Tutup program yang mungkin mengakses file.</li>"
				+ "<li>Coba mulai launcher atau Minecraft sebagai administrator.</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		// TODO: metode dihasilkan secara otomatis
		return "Aktifkan Konsol";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>Alat Debugging</b><br><br>" + "Di sini Anda dapat mengaktifkan fitur lanjutan untuk men-debug "
				+ Statics.nombre_cd.obtener() + " dan game Anda.<br><br>"
				+ "Disarankan untuk mengaktifkan konsol pengembang untuk mendapatkan informasi terperinci, jejak, dan diagnostik selama analisis.<br><br>"
				+ "Jika Anda perlu menguji server multipemain dalam mode online, mungkin perlu mengizinkan transfer token akses ke proses "
				+ Statics.nombre_cd.obtener() + " dari pengaturan privasi. "
				+ "Ini umumnya <b>tidak disarankan</b> dalam kasus lain.<br><br>"
				+ "Instruksi lengkap: <a href='https://example.com'>Tautan!</a>";// TODO
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Ketidaksesuaian terdeteksi antara Simple Clouds dan shader.</b>"
				+ "<p>Simple Clouds tidak kompatibel dengan mod shader (Iris/Oculus) saat Distant Horizons diinstal.</p>"
				+ "<p><b>Opsi yang disarankan:</b></p>" + "<ul>"
				+ "<li>Hapus <b>Simple Clouds</b> jika Anda ingin menggunakan shader.</li>"
				+ "<li>Atau, copot <b>Iris atau Oculus</b> jika Anda lebih suka mempertahankan Simple Clouds.</li>"
				+ "</ul>"
				+ "<p>Keterbatasan ini berasal dari mod Simple Clouds itu sendiri dan tidak dapat diselesaikan tanpa memodifikasi kodenya.</p>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "Ketidaksesuaian: Simple Clouds vs Shader";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "Warna tombol bilah samping";
	}

	@Override
	public String profilerTitulo() {
		return "Profiler";
	}

	@Override
	public String profilerDescripcion() {
		return "Alat analisis kinerja berdasarkan instrumentasi dan pengambilan sampel.";
	}

	@Override
	public String profilerIniciar() {
		return "Mulai";
	}

	@Override
	public String profilerDetener() {
		return "Berhenti";
	}

	@Override
	public String profilerLimpiar() {
		return "Bersihkan";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Profiler dimulai.";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Profiler berhenti.";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "Sampel diterima dari thread: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "Pengambilan sampel periodik dari stack untuk mendeteksi bottleneck dan deadlock.";
	}

	@Override
	public String entrarAlJuego() {
		// TODO: metode dihasilkan secara otomatis
		return "Masuk ke Game";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan jalur sistem terdeteksi.</b>"
				+ "<p>Minecraft tidak dapat dimulai karena ada karakter ilegal dalam nama folder.</p>"
				+ "<p>Sistem mendeteksi karakter tidak valid dalam jalur (misalnya: ':' atau simbol khusus lainnya).</p>"
				+ "<p><b>Solusi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Ganti nama folder instans atau profil.</li>"
				+ "<li>Gunakan hanya karakter ASCII dasar (A-Z, a-z, 0-9).</li>"
				+ "<li>Jangan gunakan tanda aksen, simbol khusus, spasi bermasalah, atau emoji.</li>" + "</ul>"
				+ "<p>Dengantoh valid: <b>InstanKuSaya1</b></p>"
				+ "<p>Dengantoh tidak valid: <b>InstanKuSaya🔥</b> atau <b>InstanKuSaya:Mod</b></p>";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "Jalur tidak valid: karakter tidak diizinkan";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kegagalan terdeteksi dalam shader Twilight Forest dengan GPU Intel.</b>"
				+ "<p>Kesalahan ini terkait dengan driver grafis Intel saat memuat shader dari mod Twilight Forest.</p>"
				+ "<p>Kegagalan terjadi dalam driver (igxelpicd64) dan bukan masalah langsung dari mod atau Minecraft.</p>"
				+ "<p><b>Solusi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Perbarui driver Intel ke versi terbaru yang tersedia.</li>"
				+ "<li>Coba versi spesifik 31.0.101.8331 atau 31.0.101.8247 WHQL, yang menurut umpan balik tidak mengalami masalah ini.</li>"
				+ "</ul>" + "<p>Pelacakan resmi masalah:</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>Catatan:</b> Beberapa GPU Intel yang lebih lama mungkin tidak menerima update yang memperbaiki masalah ini.</p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "Crash: Twilight Forest + Driver Intel";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge: penyedia bahasa tidak dapat dimuat";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "Penyedia tidak diketahui" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge tidak dapat memuat penyedia bahasa.</b>"
				+ "<p>Terjadi kesalahan saat menginisialisasi IModLanguageProvider.</p>"
				+ "<p><b>Penyedia yang gagal:</b> " + providerTexto + "</p>"
				+ "<p>Masalah ini biasanya terjadi ketika:</p>" + "<ul>"
				+ "<li>Dependensi yang diperlukan hilang (misalnya, Kotlin for Forge).</li>"
				+ "<li>Versi mod tidak kompatibel dengan versi Forge Anda.</li>" + "<li>File mod rusak.</li>" + "</ul>"
				+ "<p><b>Solusi yang direkomendasikan:</b></p>" + "<ul>" + "<li>Instal ulang mod yang sesuai.</li>"
				+ "<li>Verifikasi bahwa semua dependensinya diinstal.</li>"
				+ "<li>Pastikan menggunakan versi yang kompatibel dengan Forge saat ini Anda.</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "Crash: Lets Do Compat (RecipeManager intercept)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Crash terdeteksi dalam Lets Do Compat (interceptApply).</b>"
				+ "<p>Kesalahan terjadi dalam transformasi metode "
				+ "<b>RecipeManager.interceptApply</b> yang dilakukan oleh Lets Do Compat.</p>"
				+ "<p>Ini biasanya menunjukkan:</p>" + "<ul>"
				+ "<li>Ketidaksesuaian antara Lets Do Compat dan mod lain yang memodifikasi resep.</li>"
				+ "<li>Versi salah untuk versi Minecraft Anda.</li>"
				+ "<li>Konflik antara transformer (mixin/coremod).</li>" + "</ul>"
				+ "<p><b>Solusi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Coba hapus sementara Lets Do Compat untuk mengkonfirmasi konflik.</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: crash dalam Item Group (plugin tidak kompatibel)";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "Plugin tidak diketahui"
				: String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI mendeteksi crash saat membangun grup item.</b>"
				+ "<p>Satu atau lebih plugin menyebabkan kesalahan saat JEI membuat daftar bahan.</p>"
				+ "<p><b>Grup/Plugin yang terpengaruh:</b> " + listaPlugins + "</p>" + "<p>Masalah ini umum ketika:</p>"
				+ "<ul>" + "<li>Plugin JEI diimplementasikan dengan buruk atau ketinggalan zaman.</li>"
				+ "<li>Tidak ada kompatibilitas dengan versi JEI saat ini.</li>"
				+ "<li>Menggunakan Fabric API dan beberapa mod mendaftarkan Item Group dengan tidak benar.</li>"
				+ "</ul>" + "<p><b>Solusi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Perbarui JEI dan mod yang tercantum.</li>"
				+ "<li>Hapus sementara plugin yang terpengaruh untuk mengkonfirmasi konflik.</li>"
				+ "<li>Laporkan kesalahan kepada pengembang mod yang sesuai.</li>" + "</ul>"
				+ "<p>Item dari grup ini tidak akan muncul dalam daftar bahan sampai masalah diperbaiki.</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "Versi mod tidak valid (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "Tidak diketahui" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "Tidak dapat menemukan mod" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Versi mod tidak valid terdeteksi.</b>" + "<p>Versi <b>" + v
				+ "</b> tidak sesuai dengan format SemVer yang valid.</p>"
				+ "<p>Kesalahan menunjukkan pre-release kosong (diakhiri dengan '+').</p>"
				+ "<p><b>Mod yang bertanggung jawab:</b><br>" + u + "</p>"
				+ "<p><b>Solusi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Edit file mod dan perbaiki versinya.</li>"
				+ "<li>Hapus '+' di akhir jika tidak ada metadata setelahnya.</li>"
				+ "<li>Perbarui mod ke versi yang diperbaiki.</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS: Akses ilegal antar modul";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Akses ilegal antar modul (JPMS) terdeteksi.</b>"
				+ "<p>Sistem modul Java (JPMS) memblokir akses antar kelas.</p>"
				+ "<p><b>Kelas yang mencoba mengakses:</b><br>" + claseOrigen + " (modul: " + moduloOrigen + ")</p>"
				+ "<p><b>Kelas yang diblokir:</b><br>" + claseDestino + " (modul: " + moduloDestino + ")</p>"
				+ "<p>Jenis kesalahan ini terjadi ketika mod tidak mendeklarasikan dengan benar "
				+ "exports atau opens di module-info.java nya.</p>" + "<p><b>Kemungkinan penyebab:</b></p>" + "<ul>"
				+ "<li>Modul tidak mengekspor paket yang diperlukan.</li>"
				+ "<li>Direktif <b>opens</b> untuk refleksi hilang.</li>"
				+ "<li>Mod tidak dikonfigurasi dengan benar untuk JPMS.</li>" + "</ul>"
				+ "<p>Masalah ini harus diperbaiki oleh pengembang mod.</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin: kelas ditempatkan salah di paket mixin";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kelas ditempatkan dengan tidak benar di paket Mixin.</b>"
				+ "<p>Kelas normal ditempatkan di dalam paket yang dideklarasikan sebagai mixin.</p>"
				+ "<p><b>Kelas konfliktual:</b><br>" + clase + "</p>" + "<p><b>Paket mixin dideklarasikan:</b><br>"
				+ paquete + "</p>" + "<p><b>File mixin yang bertanggung jawab:</b><br>" + archivoMixin + "</p>"
				+ "<p>Kelas normal tidak boleh berada di dalam paket yang didefinisikan dalam mixins.json.</p>"
				+ "<p>Hanya kelas yang dianotasi sebagai mixin yang harus ada di paket itu.</p>"
				+ "<p><b>Solusi untuk dev:</b> Pindahkan kelas normal keluar dari paket mixin "
				+ "atau perbaiki konfigurasi file mixins.json.</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Masalah terdeteksi dengan driver GPU Matrox.</b>"
				+ "<p>Log menunjukkan bahwa kegagalan terjadi dalam library driver Matrox.</p>"
				+ "<p>GPU Matrox (terutama model G200/G400 yang digunakan di server) "
				+ "tidak dirancang untuk rendering 3D modern dan mungkin tidak mendukung "
				+ "versi OpenGL yang diperlukan oleh Minecraft.</p>" + "<p><b>Solusi yang direkomendasikan:</b></p>"
				+ "<ul>" + "<li>Perbarui driver Matrox ke versi terbaru yang tersedia.</li>"
				+ "<li>Instal driver resmi daripada driver generik sistem.</li>"
				+ "<li>Jika hardware sudah lama, gunakan GPU yang kompatibel dengan OpenGL 3.2 atau lebih tinggi.</li>"
				+ "</ul>" + "<p>Di server, GPU ini biasanya hanya untuk output video dasar "
				+ "dan bukan untuk aplikasi 3D seperti Minecraft.</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: GPU tidak kompatibel";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod tidak dapat mendeteksi GPU yang kompatibel.</b>"
				+ "<p>Mod <b>VulkanMod</b> mencoba memulai menggunakan Vulkan tetapi tidak menemukan GPU yang mendukung Vulkan dengan benar.</p>"
				+ "<p>Ini biasanya terjadi ketika:</p>" + "<ul>" + "<li>GPU tidak mendukung Vulkan.</li>"
				+ "<li>Driver GPU ketinggalan zaman atau hilang.</li>"
				+ "<li>Anda menggunakan adaptor grafis yang salah (misalnya, GPU terintegrasi alih-alih dedicated).</li>"
				+ "</ul>" + "<p><b>Solusi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Perbarui driver GPU ke versi terbaru.</li>"
				+ "<li>Verifikasi bahwa GPU Anda mendukung Vulkan.</li>"
				+ "<li>Jika Anda memiliki dua GPU, paksa penggunaan dedicated untuk Minecraft.</li>"
				+ "<li>Jika GPU Anda tidak mendukung Vulkan, copot VulkanMod.</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "RenderType tidak valid untuk outline";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod mencoba menerapkan outline ke RenderType yang tidak kompatibel.</b>"
				+ "<p>Kesalahannya adalah:</p>" + "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>Mod enchant-outline / better-enchants terdeteksi dalam laporan.</b></p>"
					+ "<p>Mod ini dikenal menyebabkan masalah ini di versi Minecraft terbaru.</p>"
					+ "<p><b>Solusi yang direkomendasikan:</b> hapus atau perbarui enchant-outline.</p>";
		} else {
			base += "<p>Masalah ini biasanya terkait dengan mod yang memodifikasi rendering "
					+ "(Entity Model Features, Entity Texture Features, Visuality atau konflik dengan Sodium).</p>"
					+ "<p><b>Solusi yang direkomendasikan:</b> perbarui atau nonaktifkan mod render satu per satu.</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – DimensionalInventory nul";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG mencoba menyimpan DimensionalInventory nul.</b>" + "<p>Game meluncurkan:</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \"d\" is null</code>"
				+ "<p>Ini adalah bug yang dikenal dari DivineRPG yang terkait dengan sistem inventori Vethean.</p>"
				+ "<p><b>Solusi yang direkomendasikan:</b></p>" + "<ul>" + "<li>Buka file konfigurasi DivineRPG.</li>"
				+ "<li>Atur <code>saferVetheanInventory=true</code></li>" + "<li>Simpan dan restart game.</li>"
				+ "</ul>"
				+ "<p>Juga disarankan untuk memperbarui DivineRPG jika ada versi yang lebih baru tersedia.</p>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "Konflik dalam Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Konflik terdeteksi dalam sistem rendering.</b>" + "<p>Game meluncurkan:</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>Kesalahan ini biasanya terkait dengan konflik antara mod render "
				+ "seperti Iris, OptiFine, VulkanMod atau lainnya yang memodifikasi pipeline grafis.</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "Kegagalan internal Feather Client";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "Modelo GeckoLib tidak ditemukan";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod tidak dapat menemukan model GeckoLib.</b>" + "<p>Model yang terpengaruh:</p>" + "<code>" + modelo
				+ "</code>" + "<p>Kesalahan ini terjadi ketika file <code>.geo.json</code> tidak ada "
				+ "atau dikonfigurasi dengan buruk dalam mod.</p>" + "<p>Penyebab yang mungkin:</p>" + "<ul>"
				+ "<li>Model dihapus tetapi masih direferensikan.</li>" + "<li>Kesalahan dalam jalur file.</li>"
				+ "<li>File hilang dalam JAR.</li>" + "<li>Versi mod yang tidak kompatibel.</li>" + "</ul>";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon mencoba memutar animasi yang tidak ada.</b>" + "<p>Animasi:</p>" + "<code>" + animacion
				+ "</code>" + "<p>Grup:</p>" + "<code>" + grupo + "</code>"
				+ "<p>Kesalahan ini biasanya terjadi ketika:</p>" + "<ul>"
				+ "<li>Versi Cobblemon yang tidak kompatibel tercampur.</li>"
				+ "<li>Addon tidak sesuai dengan versi yang terpasang.</li>"
				+ "<li>Animasi atau sumber daya internal hilang.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "Kegagalan internal Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kegagalan internal Lunar Client terdeteksi.</b>"
				+ "<p>Kesalahan berasal dari klien Lunar itu sendiri, bukan dari game dasar atau mod.</p>"
				+ "<p>Lunar Client menggunakan modifikasi internal dan kustom yang dapat "
				+ "menyebabkan ketidaksesuaian dengan mod atau konfigurasi spesifik.</p>"
				+ "<p>Disarankan untuk menguji dengan instalasi standar Minecraft "
				+ "untuk mengesampingkan masalah klien itu sendiri.</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "Akses ilegal ke metode atau bidang";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod mencoba mengakses metode atau bidang yang dilindungi/pribadi.</b>"
				+ "<p>Kelas yang bertanggung jawab:</p>" + "<code>" + clase + "</code>" + "<p>Anggota yang diakses:</p>"
				+ "<code>" + miembro + "</code>" + "<p>Kesalahan ini biasanya terjadi ketika:</p>" + "<ul>"
				+ "<li>Mod dikompilasi untuk versi Minecraft lain.</li>"
				+ "<li>Ada pencampuran pemetaan yang tidak kompatibel.</li>" + "<li>Mod sudah ketinggalan zaman.</li>"
				+ "<li>Loader yang salah digunakan (Fabric/Forge/NeoForge).</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "Kesalahan memuat datapack/resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Datapack atau resourcepack gagal dimuat.</b>" + "<p>File yang bermasalah:</p>" + "<code>" + archivo
				+ "</code>" + "<p>Paket:</p>" + "<code>" + pack + "</code>"
				+ "<p>Game tidak dapat menguraikan file ini dan itu menyebabkan kesalahan pemuatan registry.</p>"
				+ "<p>Masalah ini biasanya disebabkan oleh:</p>" + "<ul>" + "<li>JSON yang salah format.</li>"
				+ "<li>Versi paket yang tidak kompatibel.</li>"
				+ "<li>Paket ketinggalan zaman untuk versi game saat ini.</li>" + "<li>Konflik antara datapacks.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "Kesalahan kompilasi shader";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kompilasi shader gagal.</b>" + "<p>Game tidak dapat mengompilasi salah satu shader aktif.</p>"
				+ "<p>Masalah ini biasanya terkait dengan Sodium, Iris atau shaderpacks yang tidak kompatibel.</p>"
				+ "<p>Rekomendasi:</p>" + "<ul>" + "<li>Coba shader yang berbeda.</li>"
				+ "<li>Nonaktifkan shader sementara.</li>" + "<li>Perbarui driver GPU.</li>"
				+ "<li>Jika masalah berlanjut, coba mulai game tanpa Sodium.</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "Kesalahan saat membuat atau memuat model";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>Terjadi kesalahan saat mencoba membuat atau memuat model Minecraft.</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>Model yang terpengaruh: <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>Jenis kesalahan ini biasanya terjadi ketika:</p>");
		sb.append("<ul>");
		sb.append("<li>Mod memiliki model yang dikonfigurasi dengan buruk.</li>");
		sb.append("<li>JSON model rusak atau tidak lengkap.</li>");
		sb.append("<li>Ada konflik antara mod yang memodifikasi model atau rendering.</li>");
		sb.append("<li>Resource pack atau datapack berisi model yang tidak kompatibel.</li>");
		sb.append("</ul>");
		sb.append("<p>Coba identifikasi mod atau paket sumber daya mana yang menyediakan model yang ditunjukkan.</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>Kemungkinan penyebab terdeteksi:</b></p>");
		sb.append("<p>Aktivitas mod <b>Cooler Animations</b> terdeteksi dalam log.</p>");
		sb.append(
				"<p>Mod ini memodifikasi sistem animasi dan model, dan dalam beberapa kasus dapat menyebabkan kesalahan loading model.</p>");
		sb.append(
				"<p>Jika masalah berlanjut, coba mulai game tanpa Cooler Animations untuk memeriksa apakah kesalahan hilang.</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "Masalah dengan Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan terkait dengan Starlight terdeteksi.</b>"
				+ "<p>Kesalahan terjadi dalam <code>BlockStarLightEngine.initNibble</code>.</p>"
				+ "<p>Ini menunjukkan kegagalan di mesin pencahayaan mod <b>Starlight</b>.</p>"
				+ "<p>Starlight adalah mod yang sepenuhnya memodifikasi sistem pencahayaan Minecraft dan dikenal menyebabkan berbagai masalah di beberapa lingkungan mod.</p>"
				+ "<p>Ini hanya salah satu dari beberapa kesalahan yang diketahui terkait dengan Starlight.</p>"
				+ "<p>Jika masalah berlanjut, coba mulai game tanpa Starlight.</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "Masalah dengan AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Crash native terkait dengan Effekseer terdeteksi.</b>"
				+ "<p>Kesalahan terjadi dalam library native <code>EffekseerNativeForJava</code>.</p>"
				+ "<p>Library ini digunakan oleh mod <b>AAAParticles</b> yang dikembangkan oleh ChloePrime.</p>"
				+ "<p>Crash di library native biasanya menunjukkan masalah dalam mod itu sendiri atau dalam dependensi nativenya.</p>"
				+ "<p>Jika masalah berlanjut, coba mulai game tanpa AAAParticles.</p>";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Terdeteksi crash native dari lingkungan runtime Java (JVM).</b>"
				+ "<p>Jenis kesalahan ini terjadi di dalam Mesin Virtual Java itu sendiri (misalnya, di <code>jvm.dll</code>, <code>libjvm.so</code>, dll.), "
				+ "dan tidak selalu disebabkan oleh mod.</p>"
				+ "<p>Meskipun dalam kasus yang jarang terjadi dapat berasal dari mod yang menggunakan library native yang tidak kompatibel, "
				+ "<b>jauh lebih mungkin bahwa hal ini disebabkan oleh versi JVM yang cacat, rusak, atau usang</b>.</p>"
				+ "<p>Ini sangat umum jika Anda menggunakan build lama atau tidak resmi dari versi Java Anda (misalnya, build komunitas tanpa dukungan).</p>"
				+ "<p><b>Kami merekomendasikan untuk menggunakan JVM yang andal dan terawat:</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b> (stabil, teruji dengan baik, ideal untuk Windows/Linux)</li>"
				+ "<li><b>OpenLogic OpenJDK</b> (dukungan multiplatform, termasuk macOS Intel)</li>"
				+ "<li><b>Azul Zulu</b> (tersertifikasi, dengan dukungan LTS gratis)</li>"
				+ "<li><b>Oracle JDK</b> (resmi, dengan pembaruan rutin)</li>" + "</ul>"
				+ "<p>Hindari build yang tidak dikenal, kustom, atau sangat lama, karena mungkin mengandung kesalahan kritis dalam mesin JVM.</p>"
				+ "<p><b>Apakah Anda menggunakan Prism Launcher atau TLauncher?</b> Sangat mudah untuk mengatur JVM kustom: "
				+ "di Prism Launcher, pergi ke <i>Instalaciones</i> → <i>Editar instancia</i> → <i>Configuración de Java</i>; "
				+ "di TLauncher, pergi ke <i>Settings</i> → <i>Java Settings</i> dan pilih jalur JDK/JRE yang Anda unduh. "
				+ "Pengumpul sampah Anda juga mungkin mengalami masalah; dalam hal ini, Anda harus beralih ke ZGC."

				+ "Anda tidak perlu mengubah JVM sistem!</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "Konflik antara Paranoia dan C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Konflik antara mod Paranoia dan C2ME terdeteksi.</b>"
				+ "<p>Kesalahan menunjukkan bahwa <code>ThreadLocalRandom</code> diakses dari thread yang salah.</p>"
				+ "<p>Ini biasanya terjadi ketika mod <b>Paranoia</b> menjalankan kode yang tidak aman untuk multiple thread sementara <b>C2ME</b> melakukan optimisasi multithreading.</p>"
				+ "<p>Jenis konflik ini umum dengan mod yang dibuat dengan MCreator.</p>"
				+ "<p>Jika masalah berlanjut, coba mulai game tanpa Paranoia atau nonaktifkan C2ME.</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "Direktori assets Minecraft tidak ada";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft tidak dapat menemukan direktori assets.</b>"
				+ "<p>Launcher mencoba memulai game dengan jalur assets yang tidak valid.</p>"
				+ "<p>Ini berarti file resources game (assets) tidak ada atau tidak diinstal dengan benar.</p>"
				+ "<p>Masalah ini biasanya terjadi dengan instalasi Minecraft yang tidak benar atau launcher yang dikonfigurasi dengan buruk.</p>"
				+ "<p>Ini juga dapat terjadi saat menggunakan launcher tidak resmi yang menangani assets dengan tidak benar seperti FreshCraft.</p>"
				+ "<p>Jika masalah berlanjut, coba instal ulang modpack atau mulai game dari launcher resmi atau terpercaya.</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "Instal " + dependencia + " versi " + version + " atau lebih baru";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "Ganti " + dependencia + " dengan versi antara " + min + " dan " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "Mod " + mod + " memerlukan " + dependencia + " versi minimum " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "Mod " + mod + " memerlukan " + dependencia + " versi " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {

		return "Mod " + mod + " memerlukan " + dependencia + " antara " + min + " dan " + max + " (saat ini: " + actual
				+ ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "Versi Cupboard yang tidak kompatibel";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Crash yang disebabkan oleh versi Cupboard yang lama terdeteksi.</b>"
				+ "<p>Kesalahan terjadi dalam <code>ClientDenganfigCompat.setupNeoforge</code> karena "
				+ "<code>ModList.get()</code> mengembalikan <code>null</code>.</p>"
				+ "<p>Ini adalah masalah yang diketahui dalam versi lama mod <b>Cupboard</b>.</p>"
				+ "<p>Versi lama seperti <b>3.2</b> mengandung bug ini.</p>"
				+ "<p><b>Solusi:</b> perbarui Cupboard ke versi <b>3.5</b> atau lebih baru.</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		// TODO: metode dihasilkan secara otomatis
		return "Ini karena Anda di macOS dan game mencoba menggunakan OpenGL, yang tidak kompatibel dengan versi macOS terbaru. "
				+ "Anda perlu menggunakan versi Minecraft yang mendukung Metal atau menggunakan Linux jika Anda memiliki Mac Intel atau M1 atau M2 tetapi bukan M3+ atau Neo.";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "Animasi GeckoLib tidak ditemukan";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod tidak dapat memuat file animasi GeckoLib.</b>" + "<p>File yang terpengaruh:</p>" + "<code>"
				+ archivo + "</code>" + "<p>Kesalahan ini terjadi ketika file <code>.json</code> animasi tidak ada, "
				+ "memiliki kesalahan sintaks, atau jalurnya salah.</p>" + "<p>Penyebab yang mungkin:</p>" + "<ul>"
				+ "<li>File dihapus tetapi masih direferensikan dalam kode.</li>"
				+ "<li>Kesalahan sintaks dalam file JSON.</li>"
				+ "<li>Jalur yang salah didefinisikan dalam registri mod.</li>"
				+ "<li>Konflik dependensi atau versi yang tidak kompatibel.</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "Animasi GeckoLib tidak ditemukan";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod tidak dapat menemukan file animasi GeckoLib.</b>" + "<p>File yang terpengaruh:</p>" + "<code>"
				+ archivo + "</code>"
				+ "<p>Kesalahan ini terjadi ketika GeckoLib mencoba memuat animasi yang tidak ada di jalur yang ditentukan. "
				+ "Berbeda dengan kesalahan pemuatan (sintaks), kesalahan ini menunjukkan bahwa file hilang secara fisik atau jalurnya salah.</p>"
				+ "<p>Penyebab yang mungkin:</p>" + "<ul>"
				+ "<li>File <code>.json</code> dihapus atau tidak disertakan dalam JAR final mod.</li>"
				+ "<li>Kesalahan ketik dalam jalur yang ditentukan dalam kode (misalnya: 'animations' vs 'animações').</li>"
				+ "<li>Perbedaan huruf besar/kecil (OS server adalah Linux (peka) dan pengembangan di Windows (tidak peka)).</li>"
				+ "<li>Mod tidak sepenuhnya diperbarui atau dependensinya rusak.</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "Konflik Pendaftaran Duplikat";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Pesan utama: Hanya teks deskriptif yang membawa warna kesalahan
		String mensajeBase = "<span style='color:#" + color
				+ "'>Konflik kritis: Upaya telah dilakukan untuk mendaftarkan objek dua kali. " + "Mod </span>" + mod1
				+ "<span style='color:#" + color + "'> dan </span>" + mod2 + "<span style='color:#" + color
				+ "'> mencoba mendaftarkan objek yang sama. " + "Objek yang berkonflik: </span>" + objeto + "<br><br>";

		// Instruksi perbaikan
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Ini umumnya terjadi ketika dua mod berbeda menambahkan objek dengan nama yang sama "
				+ "di namespace yang sama, atau ketika ada kesalahan dalam kode salah satu mod.<br><br>"
				+ "<b>Solusi yang direkomendasikan:</b><br>" + "<ul>"
				+ "<li>Periksa apakah salah satu mod adalah pembaruan atau fork dari yang lain.</li>"
				+ "<li>Coba hapus salah satu dari dua mod yang berkonflik.</li>"
				+ "<li>Periksa file konfigurasi kedua mod untuk melihat apakah Anda dapat mengubah ID objek.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "Fabric Rendering API Hilang";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Pesan utama
		String mensajeBase = "<span style='color:#" + color
				+ "'>Mod (biasanya Porting Lib atau dependennya) telah gagal karena </span>"
				+ "Fabric Rendering API<span style='color:#" + color + "'> tidak tersedia.</span><br><br>";

		// Instruksi perbaikan (Diperbarui untuk versi modern di mana Indium usang)
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Solusi yang direkomendasikan:</b><br>"
				+ "Pesan menyarankan untuk menginstal Indium, tetapi mod ini usang dalam versi game modern.<br>"
				+ "<ul>"
				+ "<li><b>Perbarui Sodium</b> ke versi <b>0.6.0</b> atau lebih tinggi (versi ini mencakup dukungan yang diperlukan).</li>"
				+ "<li>Pastikan Anda telah menginstal <b>Fabric API</b> jika belum.</li>"
				+ "<li>Jika Anda menggunakan versi game lama (1.20.6 atau lebih rendah), maka instal Indium.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "Pembatasan dependensi tidak terpenuhi";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Pesan utama
		String mensajeBase = "<span style='color:#" + color + "'>Ditemukan </span>" + cantidad + "<span style='color:#"
				+ color + "'> pembatasan dependensi yang tidak terpenuhi.</span><br><br>";

		// Konstruksi daftar konflik
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Konflik terdeteksi dalam file berikut:</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0]; // Dependensi
				String jar = par[1]; // File JAR
				// Variabel dalam warna default, teks tetap dalam warna kesalahan
				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>File: </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>Memerlukan: </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		// Instruksi perbaikan
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Ini terjadi ketika dua atau lebih mod memerlukan versi yang berbeda dan tidak kompatibel dari library internal yang sama.<br><br>"
				+ "<b>Solusi yang direkomendasikan:</b><br>" + "<ul>"
				+ "<li>Coba perbarui atau hapus mod yang terdaftar di atas untuk mengatasi ketidaksesuaian.</li>"
				+ "<li>Jika Anda tidak menemukan versi yang kompatibel, Anda dapat mencoba mengedit file <code>mods.toml</code> secara manual di dalam file JAR mod (menggunakan kompresor seperti WinRAR atau 7-Zip) untuk mengubah atau menghapus pembatasan versi, meskipun ini dapat menyebabkan ketidakstabilan.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Pesan utama
		String mensajeBase = "<span style='color:#" + color + "'>Ditemukan </span>" + cantidad + "<span style='color:#"
				+ color + "'> pembatasan dependensi yang tidak terpenuhi.</span><br><br>";

		// Membangun daftar yang dikelompokkan berdasarkan mod
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Mod yang terlibat dan dependensi yang diminta:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				String archivo = entry.getKey();
				List<String> dependencias = entry.getValue();

				// Nama mod (warna bawaan)
				listaDetalle.append("<li><b>").append(archivo).append("</b>");

				// Daftar dependensi untuk mod ini
				listaDetalle.append("<ul>");
				for (String dep : dependencias) {
					// Dependensi (warna default)
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Tidak dapat menentukan file spesifik dari log.</span><br>");
		}

		// Instruksi perbaikan
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Kesalahan ini terjadi ketika mod menyertakan versi internal dari library (JarInJar) yang tidak kompatibel satu sama lain.<br><br>"
				+ "<b>Solusi yang direkomendasikan:</b><br>" + "<ul>"
				+ "<li>Periksa daftar di atas untuk mengidentifikasi mod mana yang meminta versi berbeda dari library yang sama.</li>"
				+ "<li>Coba perbarui kedua mod ke versi terbaru mereka.</li>"
				+ "<li>Sebagai pilihan terakhir, Anda dapat membuka file <code>.jar</code> mod dengan kompresor (seperti WinRAR), edit <code>META-INF/mods.toml</code> dan ubah kisaran versi dependensi secara manual, meskipun ini berisiko dan dapat merusak mod.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina mencegah debugging";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		// Peringatan utama
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Peringatan:</b> Mod <b>Neruina</b> gagal mencoba menangani kesalahan, yang menyembunyikan penyebab sebenarnya dari crash.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Neruina sering tidak diperlukan dan mempersulit untuk mengetahui apa yang benar-benar gagal. Disarankan untuk hapusnya untuk debugging.</span><br><br>";

		// Instruksi pemulihan
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Instruksi Pemulihan:</b><br>"
				+ "1. **MCForge**: Pergi ke '[nama_dunia]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: Pergi ke 'config/neoforge-server.toml'.<br>"
				+ "   *(Catatan: Dalam permainan lokal/Singleplayer, dunia ada di folder 'saves')*.<br>"
				+ "3. Ubah **removeErroringBlockEntities** dan **removeErroringEntities** menjadi **true**.<br><br>"
				+ "<b>Opsi lain:</b><br>"
				+ "- **MCEdit**: Gunakan untuk menghapus entitas secara manual di koordinat yang ditunjukkan.<br>"
				+ "- Jika kesalahan ini berlanjut, dimungkinkan Neruina tidak berfungsi dengan benar dan hanya menghasilkan kesalahan baru."
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "Kesalahan Apothic Attributes";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Pesan utama
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> telah mendeteksi konflik: <b>AttributeMap</b> dimodifikasi tanpa memiliki pemilik yang ditugaskan.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Ini umumnya terjadi ketika mod mencoba memodifikasi atribut entitas (seperti kesehatan, damage, kecepatan) "
				+ "pada waktu yang tidak tepat atau dengan cara yang tidak benar.</span><br><br>";

		// Catatan spesifik tentang Chest Cavity
		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>"
					+ "<b>Mod Chest Cavity telah terdeteksi dalam log.</b> "
					+ "Mod ini adalah penyebab umum kesalahan spesifik ini karena cara ia menangani atribut entitas.</span><br><br>";
		}

		// Instruksi perbaikan
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Solusi yang direkomendasikan:</b><br>"
				+ "<ul>"
				+ "<li>Jika Chest Cavity diinstal, coba perbarui atau hapus sementara untuk memverifikasi jika itu penyebabnya.</li>"
				+ "<li>Periksa apakah ada mod lain yang memodifikasi atribut mob dan coba nonaktifkannya.</li>"
				+ "<li>Cari pembaruan <b>Apothic Attributes</b> karena mungkin itu bug yang telah diperbaiki dalam versi terbaru.</li>"
				+ "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "Kesalahan DecoratedPot (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Pesan utama
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Kesalahan ketidaksesuaian dengan <b>DecoratedPotBlockEntity</b> telah terjadi.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Ini adalah masalah yang diketahui dalam versi 1.19.2 mod <b>L_Enders_Cataclysm</b>, "
				+ "di mana implementasi yang diperlukan oleh game hilang.</span><br><br>";

		// Solusi
		String solucion = "<span style='color:#" + color + "'>" + "<b>Solusi yang direkomendasikan:</b><br>"
				+ "Instal mod <b>PotFix (Cataclysm Patch)</b> untuk memperbaiki kesalahan ini.<br>"
				+ "Anda dapat mengunduhnya di sini: <a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>CurseForge - PotFix</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "Kesalahan Preloading Tricks";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Pesan utama
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Konflik yang disebabkan oleh <b>Preloading Tricks</b> terdeteksi.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Kesalahan <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "menunjukkan bahwa mod memanipulasi kelas sistem modul Java dengan cara yang tidak benar.</span><br><br>";

		// Penjelasan dan Solusi
		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> adalah mod yang dirancang terutama untuk <b>pengembang</b>. "
				+ "Melakukan operasi kompleks modifikasi kelas (mixin) pada tahap sangat awal pemuatan game, "
				+ "yang dapat dengan mudah merusak stabilitas jika ada interaksi lain.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Solusi yang direkomendasikan:</b><br>" + "<ul>"
				+ "<li>Hapus mod <b>Preloading Tricks</b>. Umumnya tidak diperlukan untuk bermain di server publik atau pack stabil.</li>"
				+ "<li>Jika Anda pengembang dan membutuhkannya untuk pengujian, tinjau konfigurasi lingkungan Anda.</li>"
				+ "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "Ketidaksesuaian Simple Radio / Lexiconfig";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Pesan utama
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Konflik antara <b>Simple Radio</b> dan <b>Lexiconfig</b> terdeteksi.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Kesalahan terjadi selama proses 'shelveLexicons', yang menunjukkan ketidaksesuaian biner antara kedua library.</span><br><br>";

		// Solusi spesifik
		String solucion = "<span style='color:#" + color + "'>" + "<b>Penyebab yang diketahui:</b><br>"
				+ "Simple Radio biasanya dirancang untuk versi lama Lexiconfig, sementara Anda memiliki versi lebih baru.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Solusi yang direkomendasikan:</b><br>" + "<ul>"
				+ "<li>Coba gunakan versi lebih lama dari <b>Lexiconfig</b>.</li>"
				+ "<li>Disarankan untuk mencoba versi <b>1.3.11</b> atau lebih lama, yang biasanya kompatibel dengan Simple Radio.</li>"
				+ "<li>Jika masalah berlanjut, verifikasi apakah ada pembaruan Simple Radio yang tersedia.</li>"
				+ "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "Kesalahan Mob AI Tweaks";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Pesan utama
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Kesalahan terkait dengan <b>Mob AI Tweaks</b> terdeteksi.</span><br><br>" + "<span style='color:#"
				+ color + "'>"
				+ "Kesalahan berasal dari Mixin (<code>$mob-ai-tweaks$onSpawned</code>) yang campur tangan "
				+ "ketika entitas muncul. Ini biasanya menunjukkan konflik dengan mod lain "
				+ "yang juga memodifikasi perilaku spawn mob.</span><br><br>";

		// Solusi
		String solucion = "<span style='color:#" + color + "'><b>Solusi yang direkomendasikan:</b><br>" + "<ul>"
				+ "<li>Coba hapus <b>Mob AI Tweaks</b> untuk memverifikasi apakah ketidakstabilan hilang.</li>"
				+ "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "Verifikasi GPU (OpenGL / Pilihan GPU)";
	}

	public String desactivar_parche_gpu() {
		return "Nonaktifkan verifikasi GPU";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Verifikator GPU mungkin telah menyebabkan game ditutup.</b>";
	}

	// ==================== NO ÓPTIMA ====================

	// ==================== GENERALES ====================

	@Override
	public String nombreVerificacionRaptorLake() {
		return "Peringatan de Estabilidad CPU Intel 13/14 Gen";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "Posible inestabilidad en procesador Intel Raptor Lake";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Prosesor " + cpu + " dengan mikrokode "
				+ microcode + " telah terdeteksi." + "</b> "
				+ "Prosesor Intel generasi ke-13 dan ke-14 telah menunjukkan masalah ketidakstabilan karena terlalu banyak voltase yang diminta, "
				+ "yang dapat mempersingkat masa pakai prosesor.<br><br>"
				+ "Disarankan untuk memperbarui mikrokode atau BIOS motherboard Anda ke versi yang menyertakan mikrokode <b>"
				+ targetMicrocode + "</b> atau lebih tinggi. "
				+ "<b>Peringatan:</b> Memperbarui BIOS memiliki risiko jika tidak dilakukan dengan benar.<br><br>"
				+ "<i>Catatan: Ini hampir pasti BUKAN penyebab crash Anda saat ini, ini hanya peringatan informasi kesehatan hardware.</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "Jangan ingatkan saya tentang ini lagi";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "Baca artikel di " + fuente;
	}

	@Override
	public String tituloMixins() {
		return "Penjelajah Mixin";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixin";
	}

	@Override
	public String mixinsRaiz() {
		return "Mixin ditemukan";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "Semua";
	}

	@Override
	public String mixinsModConMixin() {
		return "Mod dengan mixin";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "Filter menurut mod yang berisi mixin";
	}

	@Override
	public String mixinsRecargar() {
		return "Muat Ulang";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "Dekompilasi Pilihan";
	}

	@Override
	public String mixinsTargets() {
		return "Target";
	}

	@Override
	public String mixinsTarget() {
		return "Target";
	}

	@Override
	public String mixinsTargetsMetodo() {
		return "Target metode";
	}

	@Override
	public String mixinsMetodos() {
		return "Metode";
	}

	@Override
	public String mixinsCampos() {
		return "Bidang";
	}

	@Override
	public String mixinsCantidad() {
		return "Jumlah mixin";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "Detail mixin";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "Detail target";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "Alat ini menampilkan mod dengan mixin SpongePowered dan memungkinkan Anda memeriksa kelas, target, metode, dan bidangnya.";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "Gunakan pemilih atas untuk memfilter dengan mod tertentu atau tampilkan semua mod dengan mixin.";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "Perluas pohon untuk melihat setiap mixin, kelas targetnya, metode yang dianotasi, dan bidang shadow.";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "Klik kanan pada mod, mixin, target, metode, atau bidang untuk mencari kemungkinan konflik dengan mixin lain.";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "Anda dapat mendekompiilasi pilihan saat ini atau hasil konflik untuk memeriksa kode terkait.";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "Warna latar belakang pemilih mod";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "Warna latar belakang panel detail";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "Warna seleksi teks";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "Warna teks yang dipilih";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "Warna teks bantuan";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "Warna latar belakang pohon";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "Warna teks yang dipilih pohon";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "Warna latar belakang yang dipilih pohon";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "Warna perbatasan seleksi pohon";
	}

	@Override
	public String depmapTitulo() {
		return "Peta Dependensi";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "Peta";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "Dependensi";
	}

	@Override
	public String depmapRecargar() {
		return "Muat Ulang";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "Dekompiilasi Pilihan";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "Pilih mod";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "Mod dasar";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "Mod dependen";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "Paket";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "Periksa tidak selaras";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "Hasil dependensi tidak selaras";
	}

	@Override
	public String depmapClaseInexistente() {
		return "Kelas tidak ada";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "Kelas direferensikan";
	}

	@Override
	public String depmapOrigen() {
		return "Asal";
	}

	@Override
	public String depmapDestino() {
		return "Tujuan";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "Detail dependensi";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "Detail referensi";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "Metode asal";
	}

	@Override
	public String depmapModBase() {
		return "Mod dasar";
	}

	@Override
	public String depmapTodos() {
		return "Semua";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "Pilih mod";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "Pilih mod dasar, dependensi, dan paket";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "Pilih referensi atau temuan untuk mendekompilasi";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "Kesalahan saat mendekompilasi";
	}

	@Override
	public String depmapAyuda1() {
		return "Alat ini membangun peta dependensi antara mod menggunakan referensi kelas di antara mereka.";
	}

	@Override
	public String depmapAyuda2() {
		return "Tab peta menampilkan grafik gelembung dengan setiap mod terhubung ke dependensi yang digunakannya.";
	}

	@Override
	public String depmapAyuda3() {
		return "Tab dependensi mengurutkan mod dari yang paling banyak dependensi hingga yang tidak memiliki.";
	}

	@Override
	public String depmapAyuda4() {
		return "Anda dapat memeriksa referensi spesifik antara mod dan dependensinya, serta mendekompilasi kelas terkait.";
	}

	@Override
	public String depmapAyuda5() {
		return "Pemeriksaan dependensi yang tidak selaras mencari referensi ke kelas yang tidak ada dalam paket atau subpaket mod dasar.";
	}

	@Override
	public String depmapColorPanel() {
		return "Warna panel";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "Warna teks yang dipilih";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "Masalah dengan addon AzureLib";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan AzureLib terdeteksi saat memuat animasi.</b>"
				+ "<p>Game menemukan JSON animasi dengan format yang salah.</p>"
				+ "<p>Masalah ini biasanya disebabkan oleh salah satu mod atau addon yang menggunakan <b>AzureLib</b>.</p>"
				+ "<p><b>Rekomendasi:</b></p>" + "<ul>"
				+ "<li>Gunakan <b>DepMap</b> dari sidebar untuk menemukan semua addon yang bergantung pada AzureLib.</li>"
				+ "<li>Coba mulai game tanpa beberapa addon itu hingga Anda menemukan yang gagal.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "Masalah dengan Decocraft Nature";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Masalah yang disebabkan oleh Decocraft Nature terdeteksi.</b>"
				+ "<p>Kesalahan terjadi saat menginisialisasi konfigurasi mixin "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code>.</p>"
				+ "<p>Masalah ini dapat diperbaiki dengan mengedit file JAR mod.</p>" + "<p><b>Langkah:</b></p>"
				+ "<ul>"
				+ "<li>Buka file JAR dengan pengarsip seperti File Roller, Ark, WinRAR, 7-Zip, atau WinZip.</li>"
				+ "<li>Masuk ke <code>META-INF/MANIFEST.MF</code>.</li>" + "<li>Hapus baris ini:</li>" + "</ul>"
				+ "<code>MixinDenganfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>Penting:</b> pertahankan baris kosong tunggal di akhir file.</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan terdeteksi di Tetra atau salah satu addonnya.</b>"
				+ "<p>Log menunjukkan bahwa deserializer tidak ditemukan untuk jenis model yang digunakan oleh <b>Tetra</b> atau salah satu tambahannya.</p>"
				+ "<p><b>Langkah yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Pertama, hapus atau nonaktifkan <b>addon Tetra</b> dan coba lagi.</li>"
				+ "<li>Jika kesalahan berlanjut, coba hapus juga <b>Tetra</b>.</li>"
				+ "<li>Anda dapat mencoba menemukan addon terkait Tetra di <b>DepMap</b>, meskipun tidak selalu muncul di sana.</li>"
				+ "</ul>"
				+ "<p>Dalam beberapa kasus masalahnya berasal dari addon, tetapi dalam kasus lain disebabkan oleh <b>Tetra</b> itu sendiri.</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "Kesalahan deserialisasi model di Tetra";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan terdeteksi di Simple Emotes.</b>"
				+ "<p>Log berisi string <b>$simpleemotes$setupAnimTAIL</b>, yang secara langsung menunjuk ke mod <b>Simple Emotes</b>.</p>"
				+ "<p><b>Opsi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Hapus atau nonaktifkan <b>Simple Emotes</b> dan coba lagi.</li>"
				+ "<li>Jika Anda menggunakan mod yang mengubah animasi pemain atau model, periksa kemungkinan ketidaksesuaian dengan <b>Simple Emotes</b>.</li>"
				+ "<li>Perbarui <b>Simple Emotes</b> dan mod terkait animasi apa pun ke versi yang kompatibel.</li>"
				+ "</ul>"
				+ "<p>Kesalahan ini biasanya menunjukkan bahwa <b>Simple Emotes</b> terlibat langsung dalam kegagalan.</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "Kesalahan di Simple Emotes";
	}

	// En Idioma

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Peringatan tentang SKLauncher.</b>"
				+ "<p>Selama berbulan-bulan terakhir telah diamati beberapa kasus <b>korupsi</b> dan masalah lainnya yang terkait dengan <b>SKLauncher</b>.</p>"
				+ "<p>Ini tidak berarti SKLauncher selalu menjadi penyebab kesalahan, tetapi dapat berkontribusi pada masalah.</p>"
				+ "<p><b>Tanda-tanda kemungkinan korupsi:</b></p>" + "<ul>"
				+ "<li>Game ditutup sangat awal selama startup.</li>"
				+ "<li>Game juga mogok bahkan <b>tanpa mod yang diinstal</b>.</li>" + "</ul>"
				+ "<p>Jika ada salah satu kasus tersebut, coba gunakan <b>launcher lain</b> untuk memeriksa apakah masalah hilang.</p>"
				+ "<p>Lihat daftar launcher yang direkomendasikan di sini:</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>Lihat dokumentasi launcher</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "Peringatan: kemungkinan masalah dengan SKLauncher";
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
		return "Pindai server dan malware";
	}

	@Override
	public String guardEscanearServidores() {
		return "Cari server";
	}

	@Override
	public String guardEscanearMalware() {
		return "Cari malware";
	}

	@Override
	public String guardTablaServidores() {
		return "Server bermasalah";
	}

	@Override
	public String guardTablaMalware() {
		return "Temuan Malware";
	}

	@Override
	public String guardColServidor() {
		return "Server";
	}

	@Override
	public String guardColDefinicion() {
		return "Definisi";
	}

	@Override
	public String guardColMensaje() {
		return "Pesan";
	}

	@Override
	public String guardColUbicacion() {
		return "Lokasi";
	}

	@Override
	public String guardColClase() {
		return "Kelas";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "Dekompiilasi";
	}

	@Override
	public String guardCfrTitulo() {
		return "Kode terdekompilasi";
	}

	@Override
	public String guardDescripcion1() {
		return "Alat ini memungkinkan Anda mencari server bermasalah dan kemungkinan temuan malware dalam mod.";
	}

	@Override
	public String guardDescripcion2() {
		return "Mungkin ada positif palsu, terutama ketika definisi lain atau pemindai malware agresif.";
	}

	@Override
	public String guardDescripcion3() {
		return "Pemeriksaan server menggunakan definisi eksternal. Jika Anda tidak memilikinya diunduh, Anda harus mengunduhnya terlebih dahulu.";
	}

	@Override
	public String guardDescripcion4() {
		return "Jika Anda sudah memiliki definisi lokal, alat akan membiarkan Anda memutuskan apakah akan menggunakannya kembali atau memperbarui.";
	}

	@Override
	public String guardDescripcion5() {
		return "Dalam tabel malware, jika kelas tersedia, Anda dapat mendekompilasinya dengan CFR untuk memeriksanya.";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "Memindai server dan malware...";
	}

	@Override
	public String guardDefsActualizar() {
		return "Perbarui";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "Daftar TLauncher";
	}

	@Override
	public String guardErrorDescompilar() {
		return "Kesalahan saat mendekompilasi";
	}

	@Override
	public String guardColorPanel() {
		return "Warna panel";
	}

	@Override
	public String guardColorTexto() {
		return "Warna teks";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "Warna teks sekunder";
	}

	@Override
	public String guardColorTabla() {
		return "Warna tabel";
	}

	@Override
	public String guardColorSeleccion() {
		return "Warna seleksi";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "Warna teks yang dipilih";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "Bagikan instans/modpack";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "Fitur berbagi instans atau modpack belum diimplementasikan.";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "Warna tombol berbagi utama";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "Warna tombol berbagi tautan";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "Warna teks tombol berbagi";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "Bagikan instans";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "Bagikan instans";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "Format";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "Layanan unggah";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "Paket dan bagikan";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "Segarkan";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "Siap";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "Mengemas pilihan...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "Mengunggah file...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "Kesalahan";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "Kode";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "Tautan";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "Anda harus menjaga aplikasi tetap terbuka agar transfer tetap tersedia.";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "Tidak ada folder atau file yang dipilih.";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "Format itu belum didukung.";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "Layanan yang dipilih tidak tersedia.";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "Transfer dimulai dengan sukses.";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "Tidak dapat mengunggah file yang dipilih.";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "Warna panel";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "Warna teks";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "Tipe yang direkomendasikan: mods, configs, saves, worlds, datapacks, resource packs dan files opsi. Hindari menyertakan materi pribadi yang tidak perlu.";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "Ekstensi dapat menambahkan layanan upload mereka sendiri. Layanan terintegrasi default harus ditampilkan di sini.";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app: hingga 5 GiB sebagai upload normal; antara 5 dan 10 GiB memerlukan menjaga pengirim tetap terbuka. Dalam implementasi proyek saat ini, integrasi nyata masih tertunda.";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com: dirancang sebagai layanan dengan retensi sementara. Masih belum didukung oleh implementasi ini.";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent: mode paling aman sebagai distribusi P2P langsung, tanpa hosting terpusat. Masih belum didukung oleh implementasi ini.";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "Secara default folder dan file paling umum dari instance dipilih untuk memfasilitasi dukungan teknis.";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "Jika Anda menyertakan folder internal " + Statics.nombre_cd.obtener()
				+ ", konfigurasi, log, dan data tambahan juga akan berjalan, jadi Anda dapat membatalkan pilihannya jika tidak diperlukan.";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "Kemungkinan Fracturiser terdeteksi. Bukti:";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "Kemungkinan pencuri informasi terdeteksi. Bukti:";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "Kelas mencurigakan:";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "File mencurigakan:";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "Kemungkinan Bright SDK terdeteksi. Bukti:";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "Paket mencurigakan:";
	}

	@Override
	public String docsTituloVentana() {
		return "Pembaca dokumen";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>Cara menggunakan pembaca ini</b><br>"
				+ "Pilih bahasa di bagian bawah untuk melihat dokumentasi yang tersedia dalam bahasa tersebut.<br>"
				+ "Di panel kiri Anda dapat menavigasi folder dan dokumen.<br>"
				+ "Saat mengklik dokumen, kontennya akan muncul di sebelah kanan.<br>"
				+ "Tautan internal dengan protokol <b>docs://</b> membuka dokumen lain dalam pembaca yang sama ini.";
	}

	@Override
	public String docsArbolTitulo() {
		return "Dokumen";
	}

	@Override
	public String docsVisorTitulo() {
		return "Daftar Isi";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "Tidak ada dokumen untuk bahasa ini.";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "Tidak ada file Markdown yang ditemukan dalam bahasa ini.";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "Dokumen yang diminta tidak ditemukan.";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "Kesalahan saat membuka dokumen:";
	}

	@Override
	public String docsCargando() {
		return "Memuat dokumen...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "Ilustrasi tidak tersedia";
	}

	@Override
	public String colorPanelSecundario() {
		return "Warna panel sekunder";
	}

	@Override
	public String colorTextoSuave() {
		return "Warna teks lembut";
	}

	@Override
	public String colorSeleccion() {
		return "Warna seleksi";
	}

	@Override
	public String colorFondoDocumento() {
		return "Warna latar belakang dokumen";
	}

	@Override
	public String iaTituloVentana() {
		return "AI";
	}

	@Override
	public String iaTituloPrincipal() {
		return "Analisis dengan AI";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "Agen analisis kerusakan";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "Alat ini membuka agen eksternal yang dapat membantu Anda menganalisis crash, kesalahan, dan log terkait Minecraft.";
	}

	@Override
	public String iaDescripcionUso() {
		return "Untuk menggunakan sistem ini, buka tautan, masuk dengan akun Baidu, lalu gunakan agen untuk meninjau crash atau log Anda.";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "Anda memerlukan akun Baidu untuk mengakses agen.";
	}

	@Override
	public String iaCopiarEnlace() {
		return "Salin tautan";
	}

	@Override
	public String iaAbrirEnlace() {
		return "Buka tautan";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "Gambar tidak tersedia";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Potret Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "Kesalahan saat memuat potret";
	}

	public String noticiaLegalCFR() {
		return "Antarmuka pengguna grafis (GUI) ini untuk mendekompilasi mod dirancang untuk membantu pengguna mengidentifikasi penyebab kegagalan perangkat lunak. "
				+ "Namun, dekompilasi terkadang diperlukan, dan pengguna harus berhati-hati agar tidak menggunakan kode yang dihasilkan untuk melanggar hukum hak cipta. "
				+ "Disarankan untuk meninjau lisensi mod terkait sebelum menggunakan kode apa pun yang diperoleh. Selain itu, banyak mod menyediakan kode sumbernya secara resmi, "
				+ "yang umumnya lebih bersih dan lebih mudah dipahami dibandingkan hasil dekompilasi. Ingat bahwa menghormati kekayaan intelektual dan lisensi penggunaan sangat penting bagi "
				+ "komunitas pengembang mod. Anda dapat melihat Undang-Undang Hak Cipta Federal Meksiko di tautan berikut: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor (Spanish)</a> "
				+ "dan versi bahasa Inggris di sini: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>. "
				+ "Karena Anda berada di CurseForge, kami juga menyediakan Undang-Undang Hak Cipta AS dalam bahasa Inggris: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "Selain itu, pengguna disarankan untuk mempelajari hukum yang berlaku di yurisdiksi mereka masing-masing. "
				+ "GUI ini hanya ditujukan untuk pemeriksaan dasar; untuk analisis lanjutan, gunakan Enigma fork dari FabricMC yang tersedia di "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>.";
	}

	@Override
	public String colorTextoDescripcion() {
		return "Warna teks deskripsi";
	}

	@Override
	public String colorTextoEstado() {
		return "Warna teks status";
	}

	@Override
	public String colorTextoExtra() {
		return "Warna teks tambahan";
	}

	@Override
	public String colorSeparador() {
		return "Warna pemisah";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "Masalah kompatibilitas Safe Fetch dengan JDK 17";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "Masalah Safe Fetch JDK 17";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "Gunakan peluncur dengan versi JDK yang diperbarui";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "Nonaktifkan mod Spark";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "Masalah saat inisialisasi MCEF";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "Kesalahan inisialisasi MCEF";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "Hapus mod MCEF";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "Konflik antara Iris dan OptiFine terdeteksi";
	}

	@Override
	public String editar() {
		return "Edit";
	}

	@Override
	public String advertenciaHashLento() {
		return "Peringatan: proses hashing berjalan lambat";
	}

	@Override
	public String agregarArchivo() {
		return "Tambahkan file";
	}

	@Override
	public String agregarCarpeta() {
		return "Tambahkan folder";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "Perbarui JDK di macOS ke versi terbaru yang kompatibel.";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "Konflik Iris dan OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "Hapus OptiFine untuk menghindari konflik.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "Konflik terdeteksi antara ModernFix dan OptiFine.";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "Konflik ModernFix dan OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "Hapus OptiFine atau ModernFix untuk menyelesaikan konflik.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "Gunakan alternatif selain ModernFix.";
	}

	@Override
	public String error_en_garde_html() {
		return "Terjadi kesalahan terkait En Garde.";
	}

	@Override
	public String error_idletweaks_html() {
		return "Terjadi kesalahan terkait IdleTweaks.";
	}

	@Override
	public String paraBuscar() {
		return "Teks untuk dicari";
	}

	@Override
	public String filtro() {
		return "Filter (id)";
	}

	@Override
	public String criticalidad() {
		return "Tingkat keparahan (PERINGATAN/KESALAHAN/FATAL)";
	}

	@Override
	public String prioridad() {
		return "Prioritas";
	}

	@Override
	public String lista() {
		return "Pemeriksaan";
	}

	@Override
	public String colIdioma() {
		return "Bahasa";
	}

	@Override
	public String colNombre() {
		return "Nama";
	}

	@Override
	public String colResultado() {
		return "Hasil";
	}

	@Override
	public String vistaJson() {
		return "Pratinjau JSON";
	}

	@Override
	public String idiomas() {
		return "Bahasa (semua wajib)";
	}

	@Override
	public String elegirFiltro() {
		return "Pilih...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "Pilih sebuah filter";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "Filter yang tersedia";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. Perbarui semua mod yang terpengaruh. Jika masalah tetap ada, hubungi pembuat mod yang menyebabkan kesalahan ini.";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "Metode Tidak Ditemukan (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. Kesalahan ini terjadi ketika sebuah mod tidak kompatibel dengan versi game saat ini atau dengan mod lain.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. Perbarui semua mod yang terlibat. Jika masalah tetap ada, laporkan kesalahan ini kepada pembuat mod yang terpengaruh.";
	}

	@Override
	public String mensajeAyudar() {
		return "Gunakan tombol Bagikan untuk memperoleh tautan ke laporan dan log. Jika Anda seorang pembuat, edit pengaturan sesuai kebutuhan.";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "Game tidak berjalan atau macet tanpa kesalahan yang jelas.";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "Struktur file yang tidak biasa (sulit untuk di-debug). Ketidakpastian tentang bagaimana peluncur ini mengelola file dan dependensi.";
	}

	@Override
	public String tienes_mod_desanimado_titulo() {
		return "Mod yang tidak disarankan terdeteksi di instalasi Anda.";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "Mod yang tidak disarankan terdeteksi";
	}

	@Override
	public String quitarLanzador() {
		return "Hapus peluncur";
	}

	@Override
	public String editarRazones() {
		return "Edit alasan";
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Tambahkan bahasa baru";
	}

	@Override
	public String aceptar() {
		return "Terima";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Warna hasil benar";
	}

	@Override
	public String gpu_crash_causas() {
		return "Kemungkinan penyebab: Driver usang, masalah OpenGL, atau GPU lama yang tidak kompatibel.";
	}

	@Override
	public String gpu_crash_recomendaciones() {
		return "Rekomendasi: Perbarui driver, paksa penggunaan GPU yang benar, dan hindari lingkungan grafis yang bermasalah.";
	}

	@Override
	public String gpu_no_optima() {
		return "Gim tidak menggunakan GPU terbaik yang tersedia.";
	}

	@Override
	public String gpu_no_optima_detalles() {
		return "Ini dapat mengurangi kinerja (FPS rendah), tetapi biasanya tidak menyebabkan kerusakan dengan sendirinya.";
	}

	@Override
	public String gpu_recomendaciones_rendimiento() {
		return "Rekomendasi: Paksa penggunaan GPU khusus dan konfigurasikan Java/Minecraft dengan benar.";
	}

	@Override
	public String gpu_nota_precision() {
		return "<b>Catatan:</b> Sistem deteksi ini tidak 100% sempurna.";
	}

	@Override
	public String gpu_consumo_energia() {
		return "GPU yang lebih kuat mengonsumsi lebih banyak daya dan dapat mengurangi daya tahan baterai pada laptop.";
	}

	@Override
	public String gpu_parche_info() {
		return "Anda dapat menonaktifkan pemeriksaan ini menggunakan tombol solusi cepat.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "Konflik kritis: Moonlight vs Iceberg (OpenGL tanpa konteks)";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "Terdeteksi konflik antara MoniLabs dan Connector Extras. Mod-mod ini tidak kompatibel karena perubahan mereka pada KubeJS.";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "Konflik MoniLabs dan Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "Coba hapus salah satu mod (MoniLabs atau Connector Extras), karena keduanya saling bertabrakan melalui modifikasi KubeJS.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "Lihat panduan kompatibilitas Iris dan Distant Horizons, lalu perbarui Iris jika diperlukan.";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Kompatibilitas Iris dan Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "Lihat panduan kompatibilitas dan perbarui Iris ke versi yang sesuai.";
	}

	@Override
	public String colorEstadoExito() {
		return "Warna status: berhasil";
	}

	@Override
	public String colorEstadoFallo() {
		return "Warna status: gagal";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "Warna status: snapshot";
	}

	@Override
	public String colorResultadoAnadido() {
		return "Warna hasil ditambahkan";
	}

	@Override
	public String colorResultadoEliminado() {
		return "Warna hasil dihapus";
	}

	@Override
	public String colorBordeScroll() {
		return "Warna batas scroll";
	}

	@Override
	public String colorFondoPanel() {
		return "Warna latar panel";
	}

	@Override
	public String colorTextoListas() {
		return "Warna teks dalam daftar";
	}

	@Override
	public String colorBotonFondo() {
		return "Warna latar tombol";
	}

	@Override
	public String colorBordeBoton() {
		return "Warna batas tombol";
	}

	@Override
	public String colorDoradoTexto() {
		return "Warna emas teks";
	}

	@Override
	public String colorTextoPanel() {
		return "Warna teks panel";
	}

	@Override
	public String colorTextoNegro() {
		return "Warna teks hitam";
	}

	@Override
	public String colorTextoPrincipal() {
		return "Warna teks utama";
	}

	@Override
	public String colorEstado() {
		return "Warna status";
	}

	@Override
	public String comprarMC() {
		return "Beli Minecraft";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "Detail metode mixin";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "Detail bidang mixin";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "Detail konflik";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "Cari kemungkinan konflik";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "Hasil konflik";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "Kemungkinan konflik";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "Kesalahan saat mendekompilasi";
	}

	@Override
	public String mixinsColorPanel() {
		return "Warna panel mixin";
	}

	@Override
	public String mixinsColorTexto() {
		return "Warna teks mixin";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "Warna teks sekunder mixin";
	}

	@Override
	public String depmapVerReferencias() {
		return "Lihat referensi";
	}

	@Override
	public String depmapDependencias() {
		return "Dependensi";
	}

	@Override
	public String depmapDependientes() {
		return "Mod yang bergantung";
	}

	@Override
	public String depmapDependiente() {
		return "Bergantung pada";
	}

	@Override
	public String depmapSinDependencias() {
		return "Tanpa dependensi";
	}

	@Override
	public String depmapColorTexto() {
		return "Warna teks utama";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "Warna teks sekunder";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "Warna teks bantuan";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "Warna latar grafik";
	}

	@Override
	public String depmapColorListaFondo() {
		return "Warna latar daftar";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "Warna latar pohon";
	}

	@Override
	public String depmapColorNodo() {
		return "Warna node grafik";
	}

	@Override
	public String depmapColorEnlace() {
		return "Warna tautan grafik";
	}

	@Override
	public String depmapColorSeleccion() {
		return "Warna pilihan";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "Mencari server bermasalah...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "Mencari malware...";
	}

	@Override
	public String guardEstadoListo() {
		return "Siap";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "Definisi tidak ditemukan";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "Definisi server tidak ditemukan. Apakah Anda ingin mengunduhnya sekarang?";
	}

	@Override
	public String guardDefsDescargar() {
		return "Unduh";
	}

	@Override
	public String guardDefsCancelar() {
		return "Batal";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "Definisi server";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "Definisi lokal sudah ada. Apakah Anda ingin menggunakannya apa adanya atau memperbaruinya?";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "Gunakan yang lokal";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "Konflik Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "Konflik terdeteksi antara Iris dan Flywheel. Game melempar NoSuchFieldError, yang menunjukkan konflik internal antara kedua mod ini.";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – Animasi tidak ditemukan";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "Feather Client berfungsi seperti modpack bawaan dengan modifikasi internal. Ini sering menimbulkan konflik dengan Sodium dan mod performa lainnya. Disarankan menggunakan instalasi Fabric standar.";
	}

	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kemungkinan kesalahan shader dengan Oculus atau Iris terdeteksi.</b>"
				+ "<p>Log berisi <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b> "
				+ "dan <b>java.lang.RuntimeException: Unknown variable:</b>.</p>"
				+ "<p>Kombinasi ini biasanya menunjukkan masalah saat mengevaluasi variabel di dalam shader，"
				+ "yang sering berkaitan dengan <b>Oculus</b>, <b>Iris</b>, atau <b>paket shader</b> yang digunakan.</p>"
				+ "<p><b>Urutan pengujian yang disarankan：</b></p>" + "<ul>"
				+ "<li>Pertama，mulai game <b>tapa mengaktifkan shader</b>.</li>"
				+ "<li>Jika masalah berlanjut，coba mulai <b>tapa Oculus atau Iris</b>.</li>"
				+ "<li>Perbarui <b>Oculus/Iris</b>, <b>paket shader</b>, dan mod grafis terkait.</li>"
				+ "<li>Jika Anda menggunakan mod rendering atau grafis lain，periksa ketidakcocokan di antaranya.</li>"
				+ "</ul>"
				+ "<p>Dalam praktiknya，kerusakan ini biasanya berasal dari <b>paket shader</b> atau interaksinya dengan <b>Oculus/Iris</b>.</p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
		return "Kemungkinan kesalahan shader dengan Oculus/Iris";
	}

	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(tidak diketahui)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(tidak diketahui)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mencoba menggunakan item yang tidak ada.</b>" + "<p>Log berisi baris <b>Item: " + itemHtml
				+ " does not exist</b>.</p>"
				+ "<p>Ini biasanya berarti bahwa beberapa <b>datapack</b>, <b>mod</b>, atau <b>konfigurasi</b> "
				+ "merujuk ke item yang tidak ada dalam game.</p>" + "<p><b>Yang perlu diperiksa：</b></p>" + "<ul>"
				+ "<li>Periksa apakah Anda telah menginstal mod yang seharusnya menyediakan item <b>" + itemHtml
				+ "</b>.</li>"
				+ "<li>Jika ya, mungkin itu adalah <b>versi yang salah</b>, item telah diubah atau dihapus, "
				+ "atau mod tersebut memiliki masalah dan sebaiknya dihapus.</li>"
				+ "<li>Jika Anda tidak memiliki mod tersebut, coba <b>installs</b>.</li>" + "</ul>"
				+ "<p><b>Untuk mengetahui mod atau datapack mana yang meminta item tersebut：</b></p>" + "<ul>"
				+ "<li>Gunakan utilitas <b>grepr</b> di bilah sisi.</li>" + "<li><b>Jangan</b> pilih folder.</li>"
				+ "<li>Aktifkan opsi <b>search in archives</b>.</li>"
				+ "<li>Dalam teks pencarian, ketik <b>namespace</b>, yaitu bagian sebelum titik dua： " + "<b>"
				+ namespaceHtml + "</b>.</li>" + "</ul>"
				+ "<p>Ini biasanya membantu menemukan file, mod, atau datapack mana yang membuat referensi yang tidak valid.</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "Item tidak ada yang direferensikan";
	}

	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan model terdeteksi untuk Rhyhorn.</b>"
				+ "<p>Log berisi baris <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b>.</p>"
				+ "<p>Meskipun model menggunakan namespace <b>Cobblemon</b>, baris ini biasanya disebabkan oleh mod "
				+ "<b>Cobblemon: Pinkan Islands</b>, yang merupakan asal dari <b>Rhyhorn</b> tersebut.</p>"
				+ "<p><b>Yang harus dicoba：</b></p>" + "<ul>"
				+ "<li>Hapus atau nonaktifkan <b>Cobblemon: Pinkan Islands</b> dan coba lagi.</li>"
				+ "<li>Perbarui <b>Cobblemon</b> dan <b>Cobblemon: Pinkan Islands</b> ke versi yang kompatibel satu sama lain.</li>"
				+ "<li>Jika masalah dimulai setelah memperbarui salah satu mod tersebut, coba kombinasi versi yang berbeda.</li>"
				+ "</ul>"
				+ "<p>Kegagalan ini biasanya menunjukkan model yang hilang, terdaftar dengan salah, atau tidak kompatibel di dalam "
				+ "<b>Cobblemon: Pinkan Islands</b>.</p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "Kesalahan model Rhyhorn di Cobblemon: Pinkan Islands";
	}

	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan terdeteksi di Cold Sweat.</b>"
				+ "<p>Log berisi indikasi seperti <b>$cold_sweat$onBuildStart</b>, "
				+ "<b>InitDynamicTagsEvent.fillTag</b> dan <b>NullPointerException</b> di mana "
				+ "registri muncul sebagai null.</p>"
				+ "<p>Ini biasanya menunjukkan masalah pada <b>Cold Sweat</b> saat membangun atau mengisi "
				+ "<b>tag dinamis</b>, biasanya karena ketidakcocokan, kesalahan internal mod, "
				+ "atau kombinasi yang konflik dengan mod atau datapack lain.</p>" + "<p><b>Yang harus dicoba：</b></p>"
				+ "<ul>" + "<li>Hapus atau nonaktifkan <b>Cold Sweat</b> dan coba lagi.</li>"
				+ "<li>Perbarui <b>Cold Sweat</b> ke versi yang kompatibel dengan versi Minecraft dan loader Anda.</li>"
				+ "<li>Jika Anda menggunakan datapack atau mod yang mengubah <b>tag</b>, <b>bioma</b>, <b>suhu</b>, atau registri terkait, periksa juga.</li>"
				+ "<li>Jika kesalahan dimulai setelah memperbarui mod, coba kombinasi versi yang berbeda.</li>"
				+ "</ul>" + "<p>Dalam kasus ini, <b>Cold Sweat</b> terlibat langsung dalam kegagalan tersebut.</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "Kesalahan Cold Sweat pada tag dinamis";
	}

	@Override
	public String mensajeClassCastExceptionGeneral(String lineaClassCast) {
		String detalle = lineaClassCast == null || lineaClassCast.isEmpty() ? ""
				: "<p><b>Baris terdeteksi:</b></p><p><code>" + lineaClassCast + "</code></p>";

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ClassCastException terdeteksi.</b>"
				+ "<p>Ini berarti sebuah kelas diperlakukan seolah-olah itu adalah kelas atau antarmuka lain yang tidak kompatibel.</p>"
				+ detalle + "<p>Jenis kesalahan ini biasanya disebabkan oleh salah satu situasi berikut:</p>" + "<ul>"
				+ "<li><b>Dua mod yang tidak kompatibel</b> satu sama lain.</li>"
				+ "<li><b>Mixins</b>, <b>transformers</b>, atau patch yang memodifikasi kelas dan menyebabkan bagian lain dari game mengharapkan tipe yang berbeda.</li>"
				+ "<li>Mod lain yang ada di <b>stacktrace</b> yang menyebabkan miscast.</li>" + "</ul>"
				+ "<p><b>Yang perlu diperiksa:</b></p>" + "<ul>"
				+ "<li>Lihat baris-baris <b>stacktrace</b> yang terkait dengan kesalahan ini.</li>"
				+ "<li>Berikan perhatian khusus pada nama mod atau kelas dengan format <b>$modid$algo</b>, karena biasanya menunjukkan mod yang terlibat.</li>"
				+ "<li>Coba perbarui, hapus, atau pisahkan mod yang tampaknya terkait dengan konversi yang tidak valid.</li>"
				+ "</ul>"
				+ "<p>Meskipun <b>ClassCastException</b> tidak selalu fatal, sangat sering memang demikian.</p>";
	}

	@Override
	public String nombreClassCastExceptionGeneral() {
		return "ClassCastException terdeteksi";
	}

	@Override
	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Ketidakcocokan yang mungkin terjadi antara Valkyrien Skies Tournament dan Lithium/Radium terdeteksi.</b>"
				+ "<p>Log berisi <b>InvalidInjectionException</b> di mana muncul mixin dari "
				+ "<b>Lithium</b> pada <b>POI</b> bersama dengan <b>valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager</b>.</p>"
				+ "<p>Masalah ini biasanya terjadi saat menggunakan <b>versi lama Lithium</b> atau "
				+ "<b>fork berbasis Lithium lama</b>, seperti <b>Radium Reforged</b>, bersama dengan "
				+ "<b>VS Tournament</b>.</p>" + "<p><b>Yang harus dicoba：</b></p>" + "<ul>"
				+ "<li>Perbarui <b>Lithium</b> ke versi yang lebih baru dan kompatibel.</li>"
				+ "<li>Jika Anda berada di <b>Forge/NeoForge</b> dan menggunakan <b>Radium Reforged</b> atau fork lama lainnya, hapus.</li>"
				+ "<li>Sebagai gantinya, coba <b>Harium</b>, yang merupakan fork modern dari Radium yang disinkronkan dengan peningkatan terbaru dari Lithium.</li>"
				+ "<li>Jika masalah dimulai setelah memperbarui mod, periksa kombinasi yang tepat antara <b>VS Tournament</b> dan mod optimasi AI/POI Anda.</li>"
				+ "</ul>"
				+ "<p>Dalam praktiknya, kegagalan ini biasanya berasal dari implementasi lama <b>Lithium/Radium</b> yang tidak cocok dengan baik dengan <b>VS Tournament</b>.</p>";
	}

	@Override
	public String nombreValkyrienSkiesTournamentLithiumPoiInjection() {
		return "Ketidakcocokan VS Tournament dengan Lithium/Radium";
	}

	@Override
	public String mensajeVSTournamentVSConfigClassNoExiste() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VS Tournament tampaknya terlalu lama untuk versi Valkyrien Skies Anda.</b>"
				+ "<p>Log berisi <b>NoClassDefFoundError</b> untuk "
				+ "<b>org/valkyrienskies/core/impl/config/VSConfigClass</b> serta baris dari "
				+ "<b>org.valkyrienskies.tournament.TournamentMod.init(...)</b>.</p>"
				+ "<p>Ini biasanya berarti Anda memiliki <b>versi lama VS Tournament</b> yang mencoba "
				+ "menggunakan kelas internal lama dari <b>Valkyrien Skies</b> yang sudah tidak ada.</p>"
				+ "<p><b>Yang harus dilakukan：</b></p>" + "<ul>" + "<li>Hapus <b>VS Tournament</b> versi lama.</li>"
				+ "<li>Gunakan <b>VS Tournament Reforged</b> sebagai gantinya.</li>"
				+ "<li>Pastikan juga versi <b>Valkyrien Skies</b> cocok dengan versi yang didukung oleh addon tersebut.</li>"
				+ "</ul>"
				+ "<p>Rekomendasi untuk beralih ke <b>VS Tournament Reforged</b> sesuai dengan status proyek saat ini： "
				+ "versi asli Tournament masih terdaftar sebagai mod lama untuk 1.18.2, sedangkan "
				+ "<b>VS Tournament Reforged</b> dirilis secara terpisah dan saat ini mengumumkan dukungan untuk Valkyrien "
				+ "2.4.9+ di Forge 1.20.1.</p>";
	}

	@Override
	public String nombreVSTournamentVSConfigClassNoExiste() {
		return "VS Tournament lama tidak kompatibel dengan Valkyrien Skies";
	}

	public String curseForgeClaveApiMundial() {
		return "Kunci API Global CurseForge";
	}

	public String curseForgeEndpoint() {
		return "Endpoint CurseForge";
	}

	public String tlmodsEndpoint() {
		return "Endpoint TLMods";
	}

	public String minecraftStorageEndpoint() {
		return "Endpoint MinecraftStorage";
	}

	public String autoBackupActivado() {
		return "Cadangan otomatis diaktifkan";
	}

	public String autoBackupFrecuencia() {
		return "Frekuensi cadangan otomatis";
	}

	public String autoBackupDiasConservar() {
		return "Hari penyimpanan cadangan otomatis";
	}

	public String autoBackupTamanoMaximoMB() {
		return "Ukuran maksimum cadangan otomatis (MB)";
	}

	public String actualizadorModsTitulo() {
		return "Pembaruan Mod";
	}

	public String actualizadorModsBotonSidebar() {
		return "Pembaruan";
	}

	public String actualizadorModsDescripcion() {
		return "Mencari pembaruan yang tersedia untuk mod dalam modpack. Anda dapat memperbarui semua atau menerapkan pembaruan satu per satu.";
	}

	public String actualizadorModsBotonEscanear() {
		return "Periksa Pembaruan";
	}

	public String actualizadorModsBotonActualizarTodo() {
		return "Perbarui Semua";
	}

	public String actualizadorModsBotonActualizarUno() {
		return "Perbarui";
	}

	public String actualizadorModsEstadoListo() {
		return "Siap";
	}

	public String actualizadorModsEstadoEscaneando() {
		return "Memeriksa pembaruan...";
	}

	public String actualizadorModsEstadoActualizando() {
		return "Memperbarui...";
	}

	public String actualizadorModsEstadoSinActualizaciones() {
		return "Tidak ada pembaruan yang tersedia.";
	}

	public String actualizadorModsEstadoEncontradas(int n) {
		return "Pembaruan ditemukan: " + n;
	}

	public String actualizadorModsEstadoActualizadas(int n) {
		return "Pembaruan diterapkan: " + n;
	}

	public String actualizadorModsEstadoError() {
		return "Kesalahan saat memperbarui.";
	}

	public String actualizadorModsSinSeleccion() {
		return "Tidak ada pembaruan yang dipilih.";
	}

	public String actualizadorModsColumnaMod() {
		return "Mod";
	}

	public String actualizadorModsColumnaActual() {
		return "Saat Ini";
	}

	public String actualizadorModsColumnaNueva() {
		return "Baru";
	}

	public String actualizadorModsColumnaFuente() {
		return "Sumber";
	}

	public String actualizadorModsColumnaLoader() {
		return "Pemuat";
	}

	public String actualizadorModsColumnaRuta() {
		return "Jalur";
	}

	public String actualizadorModsColumnaAccion() {
		return "Tindakan";
	}

	public String actualizadorModsColorFondo() {
		return "Pembaru: latar belakang";
	}

	public String actualizadorModsColorPanel() {
		return "Pembaru: panel";
	}

	public String actualizadorModsColorTexto() {
		return "Pembaru: teks";
	}

	public String actualizadorModsColorTextoSuave() {
		return "Pembaru: teks lembut";
	}

	public String actualizadorModsColorBoton() {
		return "Pembaru: tombol";
	}

	public String actualizadorModsColorBotonTexto() {
		return "Pembaru: teks tombol";
	}

	public String actualizadorModsColorTabla() {
		return "Pembaru: tabel";
	}

	public String actualizadorModsColorSeleccion() {
		return "Pembaru: pilihan";
	}

	public String importadorYumeiriTeExtraniamos() {
		return "Kami merindukanmu, Yumeiri Reyu.";
	}

	public String importadorColorFondo() {
		return "Pengimpor: latar belakang";
	}

	public String importadorColorPanel() {
		return "Pengimpor: panel";
	}

	public String importadorColorTexto() {
		return "Pengimpor: teks";
	}

	public String importadorColorTextoSuave() {
		return "Pengimpor: teks lembut";
	}

	public String importadorColorBoton() {
		return "Pengimpor: tombol";
	}

	public String importadorColorBotonTexto() {
		return "Pengimpor: teks tombol";
	}

	public String importadorColorBorde() {
		return "Pengimpor: batas";
	}

	public String importadorConflictoTitulo() {
		return "Konflik saat mengimpor";
	}

	public String importadorConflictoMensaje() {
		return "File sudah ada di tujuan.";
	}

	public String importadorRuta() {
		return "Jalur";
	}

	public String importadorArchivoExistente() {
		return "File yang ada";
	}

	public String importadorArchivoNuevo() {
		return "File yang diimpor";
	}

	public String importadorTamano() {
		return "Ukuran";
	}

	public String importadorFecha() {
		return "Terakhir diubah";
	}

	public String importadorInfoMod() {
		return "Informasi mod";
	}

	public String importadorModImportadoMasNuevo() {
		return "Mod yang diimpor tampaknya lebih baru.";
	}

	public String importadorModImportadoMasViejo() {
		return "Mod yang diimpor tampaknya lebih lama.";
	}

	public String importadorBotonReemplazar() {
		return "Ganti";
	}

	public String importadorBotonSaltar() {
		return "Lewati";
	}

	public String importadorBotonRenombrar() {
		return "Ubah nama baru";
	}

	public String importadorModpackTitulo() {
		return "Impor Modpack";
	}

	public String importadorModpackBotonSidebar() {
		return "Impor";
	}

	public String importadorModpackDescripcion() {
		return "Impor modpack ke instance saat ini. Anda dapat menyeret file .zip, .mrpack, atau format lain yang didukung, atau memilihnya secara manual.";
	}

	public String importadorModpackFormato() {
		return "Format";
	}

	public String importadorModpackArrastraArchivo() {
		return "Seret modpack ke sini atau pilih file";
	}

	public String importadorModpackBotonSeleccionar() {
		return "Pilih File";
	}

	public String importadorModpackBotonImportar() {
		return "Impor";
	}

	public String importadorModpackSeleccionarArchivo() {
		return "Pilih Modpack";
	}

	public String importadorModpackEstadoListo() {
		return "Siap";
	}

	public String importadorModpackEstadoImportando() {
		return "Mengimpor...";
	}

	public String importadorModpackEstadoError() {
		return "Kesalahan saat mengimpor.";
	}

	public String importadorModpackSinArchivo() {
		return "Pilih file modpack terlebih dahulu.";
	}

	public String importadorModpackFormatoNoSoportado() {
		return "Format ini tidak mendukung impor.";
	}

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores) {
		return "Impor selesai.\nDisalin: " + copiados + "\nDiganti: " + reemplazados + "\nDilewati: " + saltados
				+ "\nDinamai Ulang: " + renombrados + "\nKesalahan: " + errores;
	}

	public String importadorModpackColorFondo() {
		return "Pengimpor Modpack: latar belakang";
	}

	public String importadorModpackColorPanel() {
		return "Pengimpor Modpack: panel";
	}

	public String importadorModpackColorTexto() {
		return "Pengimpor Modpack: teks";
	}

	public String importadorModpackColorTextoSuave() {
		return "Pengimpor Modpack: teks lembut";
	}

	public String importadorModpackColorBoton() {
		return "Pengimpor Modpack: tombol";
	}

	public String importadorModpackColorBotonTexto() {
		return "Pengimpor Modpack: teks tombol";
	}

	public String importadorModpackColorDrop() {
		return "Pengimpor Modpack: area seret";
	}

	public String importadorModpackColorBorde() {
		return "Pengimpor Modpack: batas";
	}

	public String jgitTituloIzzy() {
		return "Pusat Git Izzy";
	}

	public String jgitRetratoIzzy() {
		return "Izzy";
	}

	public String jgitNoHayRetratoIzzy() {
		return "Tidak ada potret Izzy";
	}

	public String jgitSeccionInstalacion() {
		return "Instalasi JGit";
	}

	public String jgitAbrirCarpetaInstalacion() {
		return "Buka folder instalasi";
	}

	public String jgitAbrirPaginaDescarga() {
		return "Buka halaman JGit";
	}

	public String jgitSeccionRepositorio() {
		return "Repositori Lokal";
	}

	public String jgitCrearRepositorioLocal() {
		return "Buat repositori Git di sini";
	}

	public String jgitCommitManual() {
		return "Commit Manual";
	}

	public String jgitSeccionRemote() {
		return "Remote";
	}

	public String jgitForgeManual() {
		return "Manual";
	}

	public String jgitForgePersonalizado() {
		return "Forge Kustom";
	}

	public String jgitEstablecerRemoteManual() {
		return "Atur remote manual";
	}

	public String jgitCrearRemoteConAPI() {
		return "Buat remote dengan API";
	}

	public String jgitPushManual() {
		return "Push Manual";
	}

	public String jgitSeccionAutomaticos() {
		return "Otomatisasi";
	}

	public String jgitAutoCommitDespuesBackup() {
		return "Commit otomatis setelah backup";
	}

	public String jgitAutoPushDespuesCommit() {
		return "Push otomatis setelah commit";
	}

	public String jgitSeccionHerramientas() {
		return "Alat";
	}

	public String jgitAbrirGuiSwing() {
		return "Buka penampil Swing JGit";
	}

	public String jgitEstado() {
		return "Status";
	}

	public String jgitClasspath() {
		return "JGit di classpath";
	}

	public String jgitTodosLosArtefactos() {
		return "Semua artefak JGit";
	}

	public String jgitRepositorio() {
		return "Repositori";
	}

	public String jgitRemote() {
		return "Remote";
	}

	public String jgitCarpetaActual() {
		return "Folder saat ini";
	}

	public String jgitNoSePudoCrearRepo() {
		return "Gagal membuat repositori.";
	}

	public String jgitEscribaRemote() {
		return "Masukkan URL remote:";
	}

	public String jgitNoSePudoGuardarRemote() {
		return "Gagal menyimpan remote.";
	}

	public String jgitApiForgeAunNoImplementada() {
		return "API forge belum diimplementasikan.";
	}

	public String jgitNoHayCambiosOError() {
		return "Tidak ada perubahan untuk commit atau terjadi kesalahan.";
	}

	public String jgitNoSePudoPush() {
		return "Gagal melakukan push.";
	}

	public String jgitTituloVentanaSwing() {
		return "Penampil Git";
	}

	public String jgitNoHayRepositorio() {
		return "Tidak ada repositori Git di folder ini.";
	}

	public String jgitArchivosModificados() {
		return "File yang dimodifikasi";
	}

	public String jgitArchivosNuevos() {
		return "File baru";
	}

	public String jgitUltimosCommits() {
		return "Commit terbaru";
	}

	public String jgitError() {
		return "Kesalahan JGit";
	}

	public String si() {
		return "Ya";
	}

	public String no() {
		return "Tidak";
	}

	public String jgitDescargarDependenciasFaltantes() {
		return "Unduh dependensi yang hilang";
	}

	public String jgitNoFaltanDependencias() {
		return "Tidak ada dependensi JGit yang hilang.";
	}

	public String jgitConfirmarDescargaDependencias(int cantidad) {
		return "Ada " + cantidad + " dependensi JGit yang hilang. Apakah Anda ingin mengunduhnya dari Maven Central?";
	}

	public String jgitDependenciasDescargadas(int cantidad) {
		return "Dependensi diunduh: " + cantidad;
	}

	public String jgitErroresDescarga() {
		return "Kesalahan pengunduhan";
	}

	public String jgitReiniciarParaCargarClasspath() {
		return "Mulai ulang " + Statics.nombre_cd.obtener() + " agar JAR baru masuk ke classpath.";
	}

	public String jgitArtefactosFaltantes() {
		return "Artefak yang hilang";
	}

	public String jgitArtefactosFaltantesClasspath() {
		return "Artefak yang hilang di classpath";
	}

	public String jgitArtefactosFaltantesCarpeta() {
		return "Artefak yang hilang di folder instalasi";
	}

	public String jgitDependenciasEnCarpeta() {
		return "Dependensi yang terinstal di folder";
	}

	public String jgitForgeNoSeleccionada() {
		return "Tidak ada forge yang dipilih.";
	}

	public String jgitForgeNoRegistrada(String id) {
		return "Forge tidak terdaftar: " + id;
	}

	public String jgitEscribaUrlForge() {
		return "URL Forge:";
	}

	public String jgitEscribaNombreRepositorio() {
		return "Nama repositori:";
	}

	public String jgitEscribaDescripcionRepositorio() {
		return "Deskripsi repositori:";
	}

	public String jgitEscribaNamespaceOpcional() {
		return "Namespace opsional:";
	}

	public String jgitEscribaTokenForge() {
		return "Token API Forge:";
	}

	public String jgitErrorCrearRemote() {
		return "Kesalahan saat membuat remote";
	}

	@Override
	public String mensajeControlifyRemoveReloadingScreen() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Ketidakcocokan terdeteksi antara Controlify dan Remove Reloading Screen.</b>"
				+ "<p>Log berisi baris <b>Attempted to fetch default config before DefaultConfigManager was ready!</b> "
				+ "dan <b>$rrls$init</b>, yang biasanya menunjukkan konflik antara <b>Controlify</b> dan "
				+ "<b>Remove Reloading Screen</b>.</p>"
				+ "<p><b>Penyebab kemungkinan:</b> Remove Reloading Screen memodifikasi bagian dari layar pemuatan atau proses pemuatan, "
				+ "sedangkan Controlify mencoba menginisialisasi konfigurasinya sebelum sistem sepenuhnya siap.</p>"
				+ "<p><b>Opsi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Hapus <b>Remove Reloading Screen</b>.</li>"
				+ "<li>Atau perbarui <b>Controlify</b> dan <b>Remove Reloading Screen</b> jika versi baru tersedia.</li>"
				+ "<li>Jika masalah berlanjut, pertahankan <b>Controlify</b> dan hapus mod apa pun yang mengubah layar pemuatan.</li>"
				+ "</ul>"
				+ "<p>Mod yang memodifikasi layar pemuatan sering menyebabkan ketidakcocokan dengan mod lain, "
				+ "dan biasanya menawarkan sedikit manfaat praktis dibandingkan dengan masalah yang dapat mereka sebabkan.</p>";
	}

	@Override
	public String nombreControlifyRemoveReloadingScreen() {
		return "Ketidakcocokan: Controlify vs Remove Reloading Screen";
	}

	@Override
	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kemungkinan masalah dengan Biomes O' Plenty dan cairan kustom.</b>"
				+ "<p>Log berisi kesalahan <b>class org.joml.Vector4f cannot be cast to class "
				+ "net.minecraft.client.renderer.fog.FogData</b> bersama dengan referensi ke <b>Biomes O' Plenty</b>.</p>"
				+ "<p>Ini mungkin terkait dengan <b>Biomes O' Plenty</b>, terutama dengan bioma, kabut "
				+ "atau cairan kustom. Namun, tidak sepenuhnya pasti bahwa Biomes O' Plenty adalah satu-satunya penyebab.</p>"
				+ "<p><b>Opsi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Coba edit data pemain untuk memindahkannya ke lokasi lain di dunia.</li>"
				+ "<li>Coba muat dunia tanpa <b>Biomes O' Plenty</b>.</li>"
				+ "<li>Jika dunia berhasil dimuat setelah memindahkan pemain, masalah kemungkinan terjadi di area tertentu, "
				+ "bioma tertentu, atau cairan kustom di dekatnya.</li>"
				+ "<li>Anda juga dapat mencoba memperbarui <b>Biomes O' Plenty</b> dan mod yang terkait dengan rendering, kabut, "
				+ "shader, atau dimensi.</li>" + "</ul>"
				+ "<p>Jika menghapus Biomes O' Plenty memungkinkan game dimulai, periksa apakah pemain berada di dalam atau dekat bioma "
				+ "atau fluida yang ditambahkan oleh mod tersebut.</p>";
	}

	@Override
	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "Masalah potensial: Biomes O' Plenty dan FogData";
	}

	@Override
	public String mensajeKotlinReflectionInternalErrorVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan refleksi internal Kotlin terdeteksi.</b>"
				+ "<p>Log berisi <b>kotlin.reflect.jvm.internal.KotlinReflectionInternalError</b> dengan pesan mirip "
				+ "<b>Property 'none' not resolved</b>.</p>"
				+ "<p>Jenis kesalahan ini umum terjadi pada versi tertentu dari <b>Fabric Language Kotlin</b> / <b>Kotlin</b>. "
				+ "Dalam kasus ini, muncul kelas dari <b>Inventory Profiles Next</b>, tetapi masalah yang sama juga dapat terjadi "
				+ "pada mod lain yang menggunakan Kotlin.</p>" + "<p><b>Opsi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Perbarui <b>Fabric Language Kotlin</b> ke versi <b>2.3.40</b>, jika tersedia untuk versi Minecraft Anda.</li>"
				+ "<li>Jika memperbarui tidak berhasil, coba turunkan <b>Fabric Language Kotlin</b> ke versi <b>2.3.10</b>.</li>"
				+ "<li>Perbarui juga <b>Inventory Profiles Next</b> jika log menyebutkan kelas dari mod tersebut.</li>"
				+ "<li>Jika kesalahan muncul dengan mod lain, periksa apakah mod tersebut bergantung pada Kotlin dan coba ubah versi "
				+ "<b>Fabric Language Kotlin</b>.</li>" + "</ul>" + "<p>Referensi teknis terkait: "
				+ "<a href='https://github.com/FabricMC/fabric-language-kotlin/issues/183'>Masalah Fabric Language Kotlin #183</a>.</p>";
	}

	@Override
	public String nombreKotlinReflectionInternalErrorVersion() {
		return "Kesalahan Kotlin: refleksi internal";
	}

	public String tituloEscanerMCreator() {
		return "Pemindai MCreator";
	}

	public String escanerMCreatorEscaneandoMods() {
		return "Memindai mod...";
	}

	public String escanerMCreatorPorFavorEspera() {
		return "Mohon tunggu.";
	}

	public String escanerMCreatorResultadosAnalisis() {
		return "Hasil analisis MCreator:";
	}

	public String escanerMCreatorNoSeEncontraronMods() {
		return "Tidak ditemukan mod MCreator.";
	}

	public String escanerMCreatorEscaneoCompletado() {
		return "Pemindaian selesai.";
	}

	public String escanerMCreatorErrorDuranteEscaneo() {
		return "Kesalahan saat memindai:";
	}

	public String escanerMCreatorCargando() {
		return "Memuat...";
	}

	public String escanerMCreatorCompletado() {
		return "Selesai";
	}

	public String escanerMCreatorError() {
		return "Kesalahan";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String textoNormal() {
		return "Teks normal";
	}

	@Override
	public String noSeEncontroConsolaParaArchivo() {
		return "Konsol tidak ditemukan untuk file: ";
	}

	@Override
	public String lineaSeleccionadaEnLectador() {
		return "baris yang dipilih dalam pembaca: ";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String mensajeMotionBlurBufferCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kemungkinan masalah dengan Motion Blur.</b>"
				+ "<p>Log berisi referensi ke <b>net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO</b> "
				+ "dan juga kesalahan <b>java.lang.IllegalStateException: Buffer already closed</b>.</p>"
				+ "<p>Baris-baris ini mungkin muncul terpisah dalam log, tetapi bersama-sama biasanya menunjukkan bahwa masalahnya terkait "
				+ "dengan mod <b>Motion Blur</b> atau penanganannya terhadap shader/buffer grafis.</p>"
				+ "<p><b>Opsi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Coba mulai game tanpa <b>Motion Blur</b>.</li>"
				+ "<li>Jika game dimulai dengan benar tanpa mod tersebut, biarkan tetap terhapus atau cari versi yang lebih baru.</li>"
				+ "<li>Anda juga dapat mencoba tanpa shaders atau mod rendering lainnya jika masalah berlanjut.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreMotionBlurBufferCerrado() {
		return "Kemungkinan masalah: Motion Blur";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String mensajeGeneratorAcceleratorOwoVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kemungkinan konflik dengan Generator Accelerator.</b>"
				+ "<p>Log berisi perbedaan antara tanda tangan <b>Found</b> dan <b>Available</b>, bersama dengan kelas dari "
				+ "<b>Generator Accelerator</b>, misalnya <b>dev/sixik/generator_accelerator/common/features/FastTarget</b>.</p>"
				+ "<p>Kesalahan ini kemungkinan disebabkan oleh <b>Generator Accelerator</b>. Ini juga bisa terkait "
				+ "dengan ketidakcocokan antara mod tersebut dan versi tertentu dari <b>owo-lib</b>.</p>"
				+ "<p><b>Opsi yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Coba mulai game tanpa <b>Generator Accelerator</b>.</li>"
				+ "<li>Jika game dimulai dengan benar, biarkan mod tersebut terhapus atau cari versi lain.</li>"
				+ "<li>Coba perbarui atau ubah versi <b>owo-lib</b>, terutama jika mod lain juga bergantung pada owo.</li>"
				+ "<li>Pastikan bahwa <b>Generator Accelerator</b>, <b>owo-lib</b>, loader, dan versi Minecraft saling kompatibel.</li>"
				+ "</ul>"
				+ "<p>Penyebab paling mungkin adalah Generator Accelerator mencoba menerapkan modifikasi dengan tanda tangan "
				+ "yang tidak cocok dengan versi saat ini dari sebuah kelas atau dependensi.</p>";
	}

	@Override
	public String nombreGeneratorAcceleratorOwoVersion() {
		return "Kemungkinan konflik: Generator Accelerator dan owo-lib";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String mensajeFabricRenderingApiFaltaIndium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Renderer yang kompatibel dengan Fabric Rendering API tidak ditemukan.</b>"
				+ "<p>Log berisi kesalahan di mana <b>RendererAccess.getRenderer()</b> mengembalikan <b>null</b>, "
				+ "menyebabkan kegagalan saat mencoba menggunakan <b>Renderer.materialFinder()</b>.</p>"
				+ "<p>Masalah ini biasanya terjadi ketika <b>Fabric Rendering API</b> tidak tersedia dengan benar. "
				+ "Penyebab umum adalah penggunaan <b>Sodium</b>, terutama versi lama yang menggantikan atau menonaktifkan bagian "
				+ "dari sistem rendering yang diharapkan oleh mod lain.</p>"
				+ "<p><b>Solusi yang direkomendasikan:</b></p>" + "<ul>" + "<li>Instal mod <b>Indium</b>.</li>"
				+ "<li>Pastikan bahwa <b>Indium</b> kompatibel dengan versi <b>Sodium</b>, Fabric Loader, dan Minecraft Anda.</li>"
				+ "<li>Jika Indium sudah terinstal, perbarui <b>Sodium</b>, <b>Indium</b>, dan <b>Fabric API</b>.</li>"
				+ "<li>Jika masalah berlanjut, coba sementara tanpa Sodium untuk memastikan bahwa kesalahan terkait dengan renderer.</li>"
				+ "</ul>"
				+ "<p>Indium biasanya memulihkan kompatibilitas dengan Fabric Rendering API untuk mod yang bergantung pada sistem tersebut "
				+ "saat Sodium terinstal.</p>";
	}

	@Override
	public String nombreFabricRenderingApiFaltaIndium() {
		return "Indium Hilang / Fabric Rendering API";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String mensajeEntradaDuplicadaIdModerno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Entri duplikat terdeteksi dalam registri Minecraft.</b>"
				+ "<p>Log berisi kesalahan yang mirip dengan <b>Duplicate entry on id</b>, misalnya "
				+ "<b>current=maroon, previous=mint</b>.</p>"
				+ "<p>Dalam versi Minecraft modern, jenis kesalahan ini biasanya terjadi ketika dua mod mencoba mendaftar "
				+ "entri berbeda menggunakan ID internal yang sama.</p>" + "<p><b>Opsi yang direkomendasikan:</b></p>"
				+ "<ul>" + "<li>Hapus salah satu mod yang mendaftarkan entri duplikat.</li>"
				+ "<li>Jika Anda mengenali nama-nama yang disebutkan dalam kesalahan, periksa mod mana yang menambahkan entri tersebut dan coba tanpa mod itu.</li>"
				+ "<li>Jika Anda tidak mengenali nama-namanya, gunakan utilitas <b>grepr</b> di bilah sisi.</li>"
				+ "<li>Di <b>grepr</b>, aktifkan pencarian di dalam file terkompresi <b>.jar</b>, <b>.zip</b>, dan <b>.fpm</b>.</li>"
				+ "<li>Aktifkan juga pencarian di <b>file biner</b>, karena beberapa nama atau ID mungkin berada di dalam kelas yang dikompilasi.</li>"
				+ "</ul>"
				+ "<li>Cari nilai-nilai yang disebutkan dalam kesalahan, seperti <b>maroon</b> atau <b>mint</b>, untuk menemukan mod yang memuatnya.</li>";
	}

	@Override
	public String nombreEntradaDuplicadaIdModerno() {
		return "Entri duplikat pada ID mod";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String nombreOpenGLMemoriaInsuficiente() {
		return "OpenGL – memori video tidak cukup";
	}

	@Override
	public String mensajeOpenGLMemoriaInsuficiente() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft menghasilkan kesalahan OpenGL karena kekurangan memori grafis.</b>"
				+ "<p>Game meluncurkan:</p>" + "<code>GL_OUT_OF_MEMORY</code>"
				+ "<p>Ini biasanya berarti kartu grafis atau sistem tidak dapat mengalokasikan memori yang cukup untuk tekstur, shader, model, buffer, atau efek visual.</p>"
				+ "<p><b>Penyebab umum:</b></p>" + "<ul>" + "<li>Shader yang terlalu berat.</li>"
				+ "<li>Paket sumber daya (resource packs) beresolusi tinggi.</li>"
				+ "<li>Terlalu banyak mod visual atau rendering.</li>" + "<li>Jarak render terlalu tinggi.</li>"
				+ "<li>VRAM tersedia sedikit.</li>" + "<li>Driver video usang atau tidak stabil.</li>" + "</ul>"
				+ "<p><b>Solusi yang direkomendasikan:</b></p>" + "<ul>" + "<li>Nonaktifkan shader sementara.</li>"
				+ "<li>Gunakan paket sumber daya dengan resolusi lebih rendah.</li>"
				+ "<li>Turunkan jarak render dan simulasi.</li>"
				+ "<li>Kurangi kualitas grafis, bayangan, partikel, dan mipmap.</li>"
				+ "<li>Perbarui driver kartu grafis.</li>"
				+ "<li>Tutup program lain yang menggunakan GPU atau banyak memori.</li>" + "</ul>"
				+ "<p>Jika kesalahan dimulai setelah menginstal shader, paket tekstur, atau mod visual, kemungkinan besar itulah penyebabnya.</p>";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String nombreErrorVerificacionBytecode() {
		return "VerifyError – bytecode atau mixin tidak valid";
	}

	@Override
	public String mensajeErrorVerificacionBytecode(String ubicacion, String razon, String claseSospechosa) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft menemukan kesalahan verifikasi bytecode.</b>"
				+ "<p>Masalah ini biasanya terjadi ketika manipulasi bytecode, transformer, atau mixin gagal.</p>"
				+ "<p>Game meluncurkan:</p>" + "<code>java.lang.VerifyError</code>"
				+ (ubicacion.length() > 0 ? "<p><b>Lokasi:</b></p><code>" + ubicacion + "</code>" : "")
				+ (razon.length() > 0 ? "<p><b>Alasan:</b></p><code>" + razon + "</code>" : "")
				+ "<p><b>Apa yang harus dicari:</b></p>" + "<ul>" + "<li>Periksa lokasi kesalahan.</li>"
				+ "<li>Cari tipe yang disebutkan di <code>Reason</code>.</li>"
				+ "<li>Tinjau stack trace untuk kelas mod yang mencurigakan.</li>"
				+ "<li>Cari nama kelas mod di sekitar kesalahan untuk mendapatkan ide.</li>" + "</ul>"
				+ "<p><b>Penggunaan Grepr yang direkomendasikan:</b></p>" + "<ul>"
				+ "<li>Buka <b>Grepr</b> di bilah sisi.</li>"
				+ "<li>Aktifkan opsi untuk mencari di dalam file <code>.jar</code>, <code>.zip</code>, atau <code>.fpm</code>.</li>"
				+ "<li>Cari nama dasar kelas, bukan necessarily paket lengkapnya.</li>" + "</ul>"
				+ "<p>Contoh: jika muncul <code>paquete.Clase</code>, cari hanya:</p>" + "<code>"
				+ (claseSospechosa.length() > 0 ? claseSospechosa : "Clase") + "</code>"
				+ "<p>Ini dapat membantu menemukan mod mana yang berisi atau memodifikasi kelas tersebut.</p>"
				+ "<p>Solusi umum: perbarui mod yang terpengaruh, hapus mod yang tidak kompatibel, periksa addon dari mod utama, atau coba tanpa mod yang menggunakan mixins/transformer pada kelas yang sama.</p>";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String errorMetodoFinalSobrescrito(String claseQueSobrescribe, String metodoFinal) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kesalahan kompatibilitas: sebuah mod mencoba menimpa metode final.</b>"
				+ "<p>Log berisi kesalahan <b>IncompatibleClassChangeError</b> dengan teks "
				+ "<b>overrides final method</b>.</p>" + "<p>Kelas yang terpengaruh: <code>" + claseQueSobrescribe
				+ "</code></p>" + "<p>Metode final yang terpengaruh: <code>" + metodoFinal + "</code></p>"
				+ "<p>Kesalahan ini biasanya terjadi ketika sebuah mod dikompilasi untuk versi Minecraft, "
				+ "Forge, NeoForge, Fabric, Quilt, atau pustaka dasar yang berbeda.</p>"
				+ "<p><b>Yang perlu dicoba:</b></p>" + "<ul>"
				+ "<li>Perbarui mod yang berisi kelas yang disebutkan.</li>"
				+ "<li>Jika masalah dimulai setelah memperbarui Minecraft atau loader, coba versi mod yang kompatibel.</li>"
				+ "<li>Jika kelas tersebut milik <b>Immersive Portals</b>, pastikan <b>Immersive Portals</b> cocok persis dengan versi Minecraft dan loader Anda.</li>"
				+ "<li>Hindari mencampur mod yang dibuat untuk versi loader atau Minecraft yang berbeda.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreErrorMetodoFinalSobrescrito() {
		return "Sebuah mod mencoba menimpa metode final";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String errorCrashProvocadoPorComando(String comandoDetectado) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft ditutup karena perintah crash.</b>" + "<p>Log menunjukkan bahwa perintah <code>"
				+ comandoDetectado + "</code> telah dijalankan.</p>"
				+ "<p>Ini biasanya berarti game tidak tertutup karena kesalahan mod normal, melainkan karena seseorang "
				+ "menggunakan perintah yang dirancang untuk memicu crash secara manual.</p>"
				+ "<p><b>Yang perlu diperiksa:</b></p>" + "<ul>"
				+ "<li>Periksa siapa yang menjalankan perintah tersebut di konsol atau di dalam game.</li>"
				+ "<li>Jika itu adalah uji coba, Anda dapat mengabaikan crash ini.</li>"
				+ "<li>Jika terjadi tanpa sengaja, periksa command block, skrip, datapack, mod administrasi, atau izin operator.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreCrashProvocadoPorComando() {
		return "Crash disebabkan oleh perintah";
	}

	// Indonesian (Bahasa Indonesia)
	public String impactoAlto() {
		return "Tinggi";
	}

	public String impactoMedio() {
		return "Sedang";
	}

	public String impactoBajo() {
		return "Rendah";
	}

	public String impactoBajoMedio() {
		return "Rendah/Sedang";
	}

	public String riesgoAlto() {
		return "Tinggi";
	}

	public String riesgoMedio() {
		return "Sedang";
	}

	public String riesgoBajo() {
		return "Rendah";
	}

	public String riesgoMedioAlto() {
		return "Sedang/Tinggi";
	}

	public String tituloCrearConfigBBE() {
		return "Buat konfigurasi Better Block Entities";
	}

	public String descripcionCrearConfigBBE() {
		return "File BBEConfig.json tidak ada.";
	}

	public String sugerenciaCrearConfigBBE() {
		return "Buat BBEConfig.json dengan optimasi untuk peti, shulker, papan tanda, tempat tidur, lonceng, dan spanduk.";
	}

	public String tituloActivarOptimizacionMaestraBBE() {
		return "Aktifkan optimasi utama BBE";
	}

	public String descripcionActivarOptimizacionMaestraBBE() {
		return "Better Block Entities tampaknya tidak memiliki optimasi utama yang diaktifkan.";
	}

	public String sugerenciaActivarOptimizacionMaestraBBE() {
		return "Tambahkan {\"option\":\"optimize.master\",\"value\":true}";
	}

	public String tituloActivarCullingTextoCartelesBBE() {
		return "Aktifkan culling teks papan tanda";
	}

	public String descripcionActivarCullingTextoCartelesBBE() {
		return "Mengurangi rendering teks papan tanda dari kejauhan.";
	}

	public String sugerenciaActivarCullingTextoCartelesBBE() {
		return "Tambahkan {\"option\":\"misc.sign_text_culling\",\"value\":true}";
	}

	public String tituloAumentarSleepDelayEntityCulling() {
		return "Tingkatkan sleepDelay EntityCulling";
	}

	public String descripcionAumentarSleepDelayEntityCulling() {
		return "EntityCulling akan memeriksa entitas lebih jarang.";
	}

	public String sugerenciaAumentarSleepDelayEntityCulling() {
		return "\"sleepDelay\": 153";
	}

	public String tituloSubirLimiteHitboxEntityCulling() {
		return "Naikkan batas hitbox";
	}

	public String descripcionSubirLimiteHitboxEntityCulling() {
		return "Memungkinkan perilaku culling yang lebih agresif sebelum jatuh ke rute yang lebih lambat.";
	}

	public String sugerenciaSubirLimiteHitboxEntityCulling() {
		return "\"hitboxLimit\": 90";
	}

	public String tituloDesactivarDatosF3EntityCulling() {
		return "Nonaktifkan data F3 EntityCulling";
	}

	public String descripcionDesactivarDatosF3EntityCulling() {
		return "Menghapus informasi debug tambahan dari mod.";
	}

	public String sugerenciaDesactivarDatosF3EntityCulling() {
		return "\"disableF3\": true";
	}

	public String tituloActivarBufferingSignsImmediatelyFast() {
		return "Aktifkan buffering eksperimental papan tanda";
	}

	public String descripcionActivarBufferingSignsImmediatelyFast() {
		return "Dapat meningkatkan kinerja saat ada banyak papan tanda.";
	}

	public String sugerenciaActivarBufferingSignsImmediatelyFast() {
		return "\"experimental_sign_text_buffering\": true";
	}

	public String tituloReducirConflictosResourcePacksImmediatelyFast() {
		return "Kurangi penanganan konflik resource pack";
	}

	public String descripcionReducirConflictosResourcePacksImmediatelyFast() {
		return "Dapat mengurangi pekerjaan tambahan, tetapi juga dapat menyebabkan masalah visual dengan resource pack.";
	}

	public String sugerenciaReducirConflictosResourcePacksImmediatelyFast() {
		return "\"experimental_disable_resource_pack_conflict_handling\": true";
	}

	public String tituloOcultarBotonNCR() {
		return "Sembunyikan tombol No Chat Reports";
	}

	public String descripcionOcultarBotonNCR() {
		return "Perubahan antarmuka; tidak banyak meningkatkan FPS, tetapi membersihkan layar.";
	}

	public String sugerenciaOcultarBotonNCR() {
		return "\"showNCRButton\": false";
	}

	public String tituloActivarMixinsExperimentalesLithium() {
		return "Aktifkan mixin eksperimental Lithium";
	}

	public String descripcionActivarMixinsExperimentalesLithium() {
		return "Mengaktifkan optimasi eksperimental tambahan.";
	}

	public String sugerenciaActivarMixinsExperimentalesLithium() {
		return "mixin.experimental=true";
	}

	public String tituloUsarDetectorThreadingPequenoFerriteCore() {
		return "Gunakan detektor threading kecil";
	}

	public String descripcionUsarDetectorThreadingPequenoFerriteCore() {
		return "Mengurangi memori, tetapi bisa lebih berisiko.";
	}

	public String sugerenciaUsarDetectorThreadingPequenoFerriteCore() {
		return "useSmallThreadingDetector=true";
	}

	public String tituloModernFixRecursosDinamicos() {
		return "Aktifkan sumber daya dinamis ModernFix";
	}

	public String descripcionModernFixRecursosDinamicos() {
		return "Dapat mengurangi penggunaan memori dan beban dengan memuat sumber daya secara lebih efisien.";
	}

	public String tituloModernFixRenderizadoresDinamicosEntidades() {
		return "Aktifkan renderer entitas dinamis";
	}

	public String descripcionModernFixRenderizadoresDinamicosEntidades() {
		return "Dapat meningkatkan kinerja dengan menangani renderer entitas secara lebih efisien.";
	}

	public String tituloModernFixRenderizadoItemsRapido() {
		return "Aktifkan rendering item cepat";
	}

	public String descripcionModernFixRenderizadoItemsRapido() {
		return "Dapat meningkatkan kinerja saat merender item.";
	}

	public String tituloModernFixWorldgenAllocation() {
		return "Kurangi alokasi pada worldgen";
	}

	public String descripcionModernFixWorldgenAllocation() {
		return "Dapat mengurangi sampah memori selama pembuatan dunia.";
	}

	public String tituloModernFixDeduplicacionIngredientes() {
		return "Aktifkan deduplikasi bahan";
	}

	public String descripcionModernFixDeduplicacionIngredientes() {
		return "Mengurangi objek duplikat terkait resep dan bahan.";
	}

	public String tituloSodiumRenderCielo() {
		return "Aktifkan optimasi/render langit di Sodium";
	}

	public String descripcionSodiumRenderCielo() {
		return "Dapat menyesuaikan perilaku rendering langit.";
	}

	public String tituloActivarLightmapCaching() {
		return "Aktifkan cache lightmap";
	}

	public String descripcionActivarLightmapCaching() {
		return "Menghindari perhitungan ulang pencahayaan saat tidak diperlukan.";
	}

	public String sugerenciaActivarLightmapCaching() {
		return "enable_lightmap_caching: true";
	}

	public String tituloOcultarTextoF3BadOptimizations() {
		return "Sembunyikan teks F3 BadOptimizations";
	}

	public String descripcionOcultarTextoF3BadOptimizations() {
		return "Mengurangi noise debug di layar F3.";
	}

	public String sugerenciaOcultarTextoF3BadOptimizations() {
		return "show_f3_text: false";
	}

	public String tituloDesactivarLogConfigBadOptimizations() {
		return "Nonaktifkan log konfigurasi";
	}

	public String descripcionDesactivarLogConfigBadOptimizations() {
		return "Mencegah pencetakan seluruh konfigurasi saat memulai.";
	}

	public String sugerenciaDesactivarLogConfigBadOptimizations() {
		return "log_config: false";
	}

	public String tituloActivarSerializadorGCFreeC2ME() {
		return "Aktifkan serialisasi GC-free C2ME";
	}

	public String descripcionActivarSerializadorGCFreeC2ME() {
		return "Mengurangi alokasi memori saat memuat atau menyimpan chunk.";
	}

	public String sugerenciaActivarSerializadorGCFreeC2ME() {
		return "[ioSystem] gcFreeChunkSerializer = true";
	}

	public String tituloDesactivarSyncPlayerTicketsC2ME() {
		return "Nonaktifkan syncPlayerTickets";
	}

	public String descripcionDesactivarSyncPlayerTicketsC2ME() {
		return "Dapat meningkatkan kinerja chunk, tetapi dapat memengaruhi contraptions teknis.";
	}

	public String sugerenciaDesactivarSyncPlayerTicketsC2ME() {
		return "[chunkSystem] syncPlayerTickets = false";
	}

	public String tituloUsarCullingHojasDepthMoreCulling() {
		return "Gunakan culling daun DEPTH";
	}

	public String descripcionUsarCullingHojasDepthMoreCulling() {
		return "Menggunakan mode culling daun yang lebih agresif daripada mode normal.";
	}

	public String sugerenciaUsarCullingHojasDepthMoreCulling() {
		return "leavesCullingMode = \"DEPTH\"";
	}

	public String tituloActivarEndGatewayCullingMoreCulling() {
		return "Aktifkan culling End Gateway";
	}

	public String descripcionActivarEndGatewayCullingMoreCulling() {
		return "Mencegah rendering End Gateway yang tidak perlu.";
	}

	public String sugerenciaActivarEndGatewayCullingMoreCulling() {
		return "endGatewayCulling = true";
	}

	public String tituloActivarActivationRangeServerCore() {
		return "Aktifkan activation range";
	}

	public String descripcionActivarActivationRangeServerCore() {
		return "Mengurangi tick entitas yang jauh dari pemain.";
	}

	public String sugerenciaActivarActivationRangeServerCore() {
		return "activation-range: enabled: true";
	}

	public String tituloActivarRangoVerticalServerCore() {
		return "Aktifkan jangkauan vertikal";
	}

	public String descripcionActivarRangoVerticalServerCore() {
		return "Mengurangi tick entitas yang berada sangat di atas atau di bawah pemain.";
	}

	public String sugerenciaActivarRangoVerticalServerCore() {
		return "use-vertical-range: true";
	}

	// Indonesian (Bahasa Indonesia)
	public String impactoNegativoAlto() {
		return "Dampak negatif tinggi";
	}

	public String advertenciaModsCulling() {
		return "Mod culling dapat menyebabkan ketidakcocokan dengan beberapa mod, crash, kesalahan di mana game berhenti melakukan tick dengan benar, dan juga dapat membuat pertanian otomatis atau pabrik berhenti bekerja.";
	}

	public String tituloModBadOptimizations() {
		return "Tambahkan BadOptimizations";
	}

	public String descripcionModBadOptimizations() {
		return "Menambahkan mikro-optimasi klien seperti cache lightmap, cache langit, dan pengurangan pemanggilan yang tidak perlu.";
	}

	public String tituloModBBE() {
		return "Tambahkan Better Block Entities";
	}

	public String descripcionModBBE() {
		return "Mengoptimalkan rendering entitas blok seperti peti, shulker, tempat tidur, lonceng, spanduk, dan papan tanda.";
	}

	public String tituloModC2ME() {
		return "Tambahkan Concurrent Chunk Management Engine";
	}

	public String descripcionModC2ME() {
		return "Meningkatkan pemuatan, generasi, dan pengelolaan chunk menggunakan pemrosesan konkuren. Bisa sangat kuat, tetapi juga dapat menyebabkan ketidakcocokan di modpack besar.";
	}

	public String tituloModEntityCulling() {
		return "Tambahkan EntityCulling";
	}

	public String descripcionModEntityCulling() {
		return "Mencegah rendering entitas yang tidak terlihat. " + advertenciaModsCulling();
	}

	public String tituloModFerriteCore() {
		return "Tambahkan FerriteCore";
	}

	public String descripcionModFerriteCore() {
		return "Mengurangi penggunaan memori melalui deduplikasi dan struktur internal yang lebih efisien.";
	}

	public String tituloModImmediatelyFast() {
		return "Tambahkan ImmediatelyFast";
	}

	public String descripcionModImmediatelyFast() {
		return "Mengoptimalkan berbagai bagian dari immediate rendering, teks, buffer, peta, dan antarmuka.";
	}

	public String tituloModLithium() {
		return "Tambahkan Lithium";
	}

	public String descripcionModLithium() {
		return "Mengoptimalkan logika game, entitas, blok, fisika, dan sistem lainnya tanpa terlalu banyak mengubah perilaku vanilla.";
	}

	public String tituloModModernFix() {
		return "Tambahkan ModernFix";
	}

	public String descripcionModModernFix() {
		return "Menambahkan banyak optimasi memori, pemuatan, sumber daya, dan kinerja umum. Alat terkait atlasnya dapat bentrok dengan mod yang membuat atlas lebih kecil.";
	}

	public String tituloModMoreCulling() {
		return "Tambahkan More Culling";
	}

	public String descripcionModMoreCulling() {
		return "Menambahkan culling untuk blok, daun, bingkai item, lukisan, hujan, beacon, dan elemen lainnya. "
				+ advertenciaModsCulling();
	}

	public String tituloModScalableLux() {
		return "Tambahkan ScalableLux";
	}

	public String descripcionModScalableLux() {
		return "Mengoptimalkan perhitungan terkait pencahayaan dan dapat meningkatkan kinerja di dunia dengan banyak perubahan cahaya.";
	}

	public String tituloModServerCore() {
		return "Tambahkan ServerCore";
	}

	public String descripcionModServerCore() {
		return "Menambahkan optimasi sisi server, activation range, kontrol mobcap, pengurangan tick, dan peningkatan pemuatan.";
	}

	public String tituloModSodium() {
		return "Tambahkan Sodium";
	}

	public String descripcionModSodium() {
		return "Mod utama optimasi rendering. Biasanya merupakan salah satu peningkatan paling penting untuk FPS.";
	}

	public String tituloModVMP() {
		return "Tambahkan Very Many Players";
	}

	public String descripcionModVMP() {
		return "Mengoptimalkan sistem server untuk menangani banyak pemain. ID mod yang diharapkan adalah vmp.";
	}

	public String tituloModMCMT() {
		return "Tambahkan MCMT";
	}

	public String descripcionModMCMT() {
		return "Mencoba melakukan multithreading pada bagian server Minecraft. Dapat meningkatkan kinerja dalam beberapa kasus, tetapi memiliki risiko tinggi ketidakcocokan, kesalahan ticking, dan perilaku aneh.";
	}

	public String tituloLiabilityUranium() {
		return "Hapus Uranium";
	}

	public String descripcionLiabilityUranium() {
		return "Uranium adalah mod yang dirancang untuk sengaja membuat game lag. Sebaiknya tidak diinstal jika Anda menginginkan kinerja yang baik.";
	}

	// Indonesian (Bahasa Indonesia)
	public String tituloAmbientalSinXmx() {
		return "Atur memori maksimal Java";
	}

	public String descripcionAmbientalSinXmx(int mods, String minimo, String maximoSeguro) {
		return "-Xmx tidak terdeteksi dalam argumen yang diberikan. Untuk " + mods
				+ " mod, minimum yang disarankan adalah " + minimo + ", jangan melebihi sekitar " + maximoSeguro + ".";
	}

	public String sugerenciaAmbientalSinXmx(String minimo) {
		return "Tambahkan -Xmx" + minimo.replace(" ", "");
	}

	public String tituloAmbientalDemasiadaMemoria() {
		return "Kurangi memori yang dialokasikan";
	}

	public String descripcionAmbientalDemasiadaMemoria(String xmx, String total, String maximoSeguro) {
		return "Instansi telah mengalokasikan " + xmx + " dari " + total
				+ ". Tidak disarankan mengalokasikan lebih dari 80% dari RAM yang tersedia.";
	}

	public String sugerenciaAmbientalDemasiadaMemoria(String maximoSeguro) {
		return "Kurangi -Xmx menjadi " + maximoSeguro + " atau kurang.";
	}

	public String tituloAmbientalMemoriaInsuficiente() {
		return "Tambahkan memori yang dialokasikan";
	}

	public String descripcionAmbientalMemoriaInsuficiente(int mods, String xmx, String minimo) {
		return "Instansi memiliki " + xmx + " yang dialokasikan. Untuk " + mods
				+ " mod, minimum yang disarankan adalah " + minimo + ".";
	}

	public String sugerenciaAmbientalMemoriaInsuficiente(String minimo) {
		return "Tingkatkan -Xmx menjadi setidaknya " + minimo + ".";
	}

	public String tituloAmbientalJava8GC() {
		return "Gunakan G1GC atau Shenandoah di Java 8";
	}

	public String descripcionAmbientalJava8GC() {
		return "Di Java 8, disarankan menggunakan G1GC atau Shenandoah untuk mengurangi jeda dan meningkatkan stabilitas.";
	}

	public String sugerenciaAmbientalJava8GC() {
		return "Tambahkan -XX:+UseG1GC atau -XX:+UseShenandoahGC.";
	}

	public String tituloAmbientalZGC() {
		return "Gunakan ZGC";
	}

	public String descripcionAmbientalZGC(String ramTotal) {
		return "Perangkat memiliki lebih dari 12 GB RAM (" + ramTotal
				+ "). Jika distribusi Java mendukungnya, ZGC dapat mengurangi jeda pengumpul sampah.";
	}

	public String sugerenciaAmbientalZGC() {
		return "Di Java 17 atau lebih tinggi, coba -XX:+UseZGC.";
	}

	public String tituloAmbientalAikar() {
		return "Tambahkan flag Aikar";
	}

	public String descripcionAmbientalAikar() {
		return "Di Java 17 atau sebelumnya, flag Aikar biasanya meningkatkan perilaku G1GC untuk Minecraft.";
	}

	public String sugerenciaAmbientalAikar() {
		return "Gunakan flag Aikar, termasuk -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200.";
	}

	public String tituloAmbientalRedHatJDK() {
		return "Gunakan Red Hat JDK";
	}

	public String descripcionAmbientalRedHatJDK(int javaMayor, String os) {
		return "Untuk Java " + javaMayor + " di " + os
				+ ", Red Hat JDK direkomendasikan karena stabilitas dan kompatibilitasnya.";
	}

	public String sugerenciaAmbientalRedHatJDK() {
		return "Instal Red Hat JDK untuk Java 8 atau Java 11.";
	}

	public String tituloAmbientalAzulPrime() {
		return "Pertimbangkan Azul Prime";
	}

	public String descripcionAmbientalAzulPrime() {
		return "Di Linux dengan Java 16 atau lebih tinggi dan lebih dari 16 GB RAM, Azul Prime bisa menjadi pilihan kinerja yang baik.";
	}

	public String sugerenciaAmbientalAzulPrime() {
		return "Coba Azul Prime jika perangkat memiliki lebih dari 16 GB RAM.";
	}

	public String tituloAmbientalGraalVM() {
		return "Pertimbangkan GraalVM";
	}

	public String descripcionAmbientalGraalVM() {
		return "Dengan Java 16 atau lebih tinggi dan lebih dari 16 GB RAM, GraalVM bisa menjadi alternatif yang berguna di luar Linux.";
	}

	public String sugerenciaAmbientalGraalVM() {
		return "Coba GraalVM jika perangkat memiliki lebih dari 16 GB RAM.";
	}

	public String tituloAmbientalDiscoBajo() {
		return "Periksa ruang kosong pada disk";
	}

	public String descripcionAmbientalDiscoBajo(String libre) {
		return "Disk memiliki sedikit ruang kosong: " + libre
				+ ". Minecraft dapat gagal, menyimpan dengan lambat, atau merusak data jika kehabisan ruang.";
	}

	public String sugerenciaAmbientalDiscoBajo() {
		return "Bebaskan ruang hingga setidaknya 20 GB tersedia.";
	}

	public String tituloAmbientalWindowsRHEL9() {
		return "Pertimbangkan RHEL 9 untuk pengujian";
	}

	public String descripcionAmbientalWindowsRHEL9() {
		return "Di Windows, disarankan mempertimbangkan RHEL 9 karena mencakup Red Hat JDK, stabil, dapat diunduh gratis untuk individu, dan merupakan tempat sebagian besar pengujian dilakukan.";
	}

	public String sugerenciaAmbientalWindowsRHEL9() {
		return "Coba instansi di RHEL 9 jika Anda mencari stabilitas pengujian maksimal.";
	}

	public String tituloAmbientalRaptorLake() {
		return "Peringatan Intel Raptor Lake";
	}

	public String descripcionAmbientalRaptorLake() {
		return "Masalah Raptor Lake yang ditandai oleh pemeriksaan yang ada telah terdeteksi. Ini dapat menyebabkan ketidakstabilan, crash, dan kesalahan yang tampak seperti berasal dari modpack.";
	}

	public String sugerenciaAmbientalRaptorLake() {
		return "Perbarui BIOS/mikrokode dan periksa peringatan Raptor Lake sebelum menyalahkan modpack.";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String tituloAmbientalNeoForge1201Antiguo() {
		return "NeoForge 1.20.1 lama terdeteksi";
	}

	@Override
	public String descripcionAmbientalNeoForge1201Antiguo() {
		return "FancyModLoader 47 atau jalur yang kompatibel dengan NeoForge 1.20.1 terdeteksi. "
				+ "NeoForge 1.20.1 adalah fork dari MinecraftForge 1.20.1 dan biasanya kompatibel secara biner, "
				+ "tetapi jalur tersebut ditinggalkan lebih awal dan mungkin kekurangan beberapa optimasi yang tersedia di Forge.";
	}

	@Override
	public String sugerenciaAmbientalNeoForge1201Antiguo() {
		return "Untuk 1.20.1, jika modpack mengizinkannya, pertimbangkan untuk menggunakan MinecraftForge 1.20.1 alih-alih NeoForge 1.20.1.";
	}

	@Override
	public String tituloAmbientalGPU() {
		return "Masalah GPU terdeteksi";
	}

	@Override
	public String descripcionAmbientalGPU() {
		return "Pemeriksaan lain sudah mendeteksi kemungkinan masalah GPU, OpenGL, atau pemilihan kartu grafis.";
	}

	@Override
	public String sugerenciaAmbientalGPU() {
		return "Periksa apakah Minecraft menggunakan GPU yang benar, perbarui driver, dan hindari konfigurasi hybrid yang tidak stabil.";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String gpuFixTitulo() {
		return "Konfigurasi GPU";
	}

	@Override
	public String gpuFixBotonSidebar() {
		return "GPU";
	}

	@Override
	public String gpuFixBotonAplicar() {
		return "Terapkan konfigurasi";
	}

	@Override
	public String gpuFixBotonFuenteTLauncher() {
		return "Buka panduan TLauncher";
	}

	@Override
	public String gpuFixBotonVirusTotal() {
		return "Buka analisis VirusTotal";
	}

	@Override
	public String gpuFixBotonOptimusLinux() {
		return "Buka panduan NVIDIA Optimus";
	}

	@Override
	public String gpuFixTextoWindows() {
		return Statics.nombre_cd.obtener()
				+ " mendeteksi bahwa Minecraft mungkin tidak menggunakan GPU berkinerja tinggi.\n\n"
				+ "Di Windows, kunci registri dapat diatur di "
				+ "HKEY_CURRENT_USER\\\\Software\\\\Microsoft\\\\DirectX\\\\UserGpuPreferences "
				+ "untuk memaksa javaw.exe menggunakan GPU dedicated.\n\n"
				+ "GpuPreference=0 = keputusan otomatis Windows.\n"
				+ "GpuPreference=1 = hemat daya / GPU terintegrasi.\n" + "GpuPreference=2 = GPU berkinerja tinggi.\n\n"
				+ "Sebagian informasi ini diperoleh berkat penelitian yang diterbitkan oleh TLauncher dan analisis "
				+ "perilaku yang tersedia di VirusTotal.";
	}

	@Override
	public String gpuFixTextoLinux() {
		return Statics.nombre_cd.obtener() + " mendeteksi masalah potensial terkait NVIDIA Optimus atau PRIME.\n\n"
				+ "Tergantung pada distribusi Linux yang digunakan, mungkin perlu mengkonfigurasi NVIDIA Optimus, "
				+ "nvidia-prime, switcheroo-control, atau sistem hybrid lainnya.\n\n"
				+ "Di Fedora/RHEL dan turunannya, biasanya disarankan untuk mengikuti dokumentasi RPMFusion.\n\n"
				+ "Tombol di bawah akan membuka dokumentasi resmi yang direkomendasikan.";
	}

	@Override
	public String gpuFixTextoMac() {
		return Statics.nombre_cd.obtener() + " mendeteksi masalah potensial dalam pemilihan GPU.\n\n"
				+ "Pada beberapa sistem macOS dengan GPU hybrid, dimungkinkan untuk memaksa penggunaan GPU dedicated "
				+ "melalui konfigurasi sistem lanjutan.\n\n"
				+ "Tombol terapkan akan mencoba menjalankan perintah untuk memprioritaskan GPU berkinerja tinggi.";
	}

	@Override
	public String gpuFixTextoOtroSistema() {
		return Statics.nombre_cd.obtener() + " mendeteksi masalah potensial terkait GPU, "
				+ "tetapi tidak ada implementasi spesifik untuk sistem operasi ini.";
	}

	@Override
	public String gpuFixLinuxManual() {
		return "Di Linux, konfigurasi biasanya harus dilakukan secara manual tergantung pada distribusi, "
				+ "driver NVIDIA, dan sistem Optimus/PRIME yang digunakan.";
	}

	@Override
	public String gpuFixSistemaNoSoportado() {
		return "Sistem operasi tidak didukung untuk konfigurasi GPU otomatis.";
	}

	@Override
	public String gpuFixJavaNoDetectado() {
		return "Tidak dapat mendeteksi jalur saat ini dari javaw.exe.";
	}

	@Override
	public String gpuFixWindowsAplicado(String ruta) {
		return "Konfigurasi GPU berhasil diterapkan untuk:\n\n" + ruta + "\n\n"
				+ "GpuPreference=2 menunjukkan GPU berkinerja tinggi.";
	}

	@Override
	public String gpuFixErrorAplicando() {
		return "Terjadi kesalahan saat mencoba menerapkan konfigurasi GPU";
	}

	@Override
	public String gpuFixMacAplicado() {
		return "Konfigurasi GPU berkinerja tinggi telah diterapkan.";
	}

	@Override
	public String gpuFixMacError() {
		return "Tidak dapat menerapkan konfigurasi GPU di macOS";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String rendimientoTitulo() {
		return "Manajer Kinerja";
	}

	@Override
	public String rendimientoBotonSidebar() {
		return "Kinerja";
	}

	@Override
	public String rendimientoBotonAnalizar() {
		return "Analisis Kinerja";
	}

	@Override
	public String rendimientoBotonAbrirGPU() {
		return "Buka Konfigurasi GPU";
	}

	@Override
	public String rendimientoDescripcion() {
		return "Panel ini memeriksa masalah lingkungan, mod yang direkomendasikan atau berisiko, dan opsi konfigurasi yang dapat meningkatkan kinerja. Tidak semua opsi bekerja bersama, tidak semua cocok untuk setiap versi Minecraft, dan tidak semua kompatibel dengan setiap pemuat mod. Itu tidak masalah: Anda tidak memerlukan skor optimasi yang sempurna.";
	}

	@Override
	public String rendimientoNotaCompatibilidad() {
		return "Catatan: saran-saran ini adalah kemungkinan, bukan perintah untuk menerapkan semuanya. Beberapa opsi mungkin bentrok satu sama lain atau tidak cocok untuk versi, peluncur, pemuat mod, atau modpack Anda.";
	}

	@Override
	public String rendimientoPestanaResumen() {
		return "Ringkasan";
	}

	@Override
	public String rendimientoPestanaAmbiental() {
		return "Masalah Lingkungan";
	}

	@Override
	public String rendimientoPestanaMods() {
		return "Mod yang Direkomendasikan dan Risiko";
	}

	@Override
	public String rendimientoPestanaConfigs() {
		return "Opsi Konfigurasi";
	}

	@Override
	public String rendimientoResumenTitulo() {
		return "Ringkasan Analisis";
	}

	@Override
	public String rendimientoResumenAmbiental(int cantidad) {
		return "Masalah lingkungan ditemukan: " + cantidad;
	}

	@Override
	public String rendimientoResumenMods(int cantidad) {
		return "Saran atau risiko mod ditemukan: " + cantidad;
	}

	@Override
	public String rendimientoResumenConfigs(int cantidad) {
		return "Saran konfigurasi ditemukan: " + cantidad;
	}

	@Override
	public String rendimientoResumenGPU() {
		return "Masalah GPU terdeteksi. Oleh karena itu, tombol untuk membuka konfigurasi GPU diaktifkan.";
	}

	@Override
	public String rendimientoSinHallazgos() {
		return "Tidak ada saran yang ditemukan di bagian ini.";
	}

	@Override
	public String rendimientoSugerencia() {
		return "Saran";
	}

	@Override
	public String rendimientoColorFondo() {
		return "Kinerja - latar belakang";
	}

	@Override
	public String rendimientoColorPanel() {
		return "Kinerja - panel";
	}

	@Override
	public String rendimientoColorTexto() {
		return "Kinerja - teks";
	}

	@Override
	public String rendimientoColorTextoSecundario() {
		return "Kinerja - teks sekunder";
	}

	@Override
	public String rendimientoColorBoton() {
		return "Kinerja - tombol";
	}

	@Override
	public String rendimientoColorBotonTexto() {
		return "Kinerja - teks tombol";
	}

	@Override
	public String rendimientoColorSeleccion() {
		return "Kinerja - seleksi";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String mensajeDialogoCompartirPrimitiva() {
		return "Anda mengalami crash. Jika tidak muncul jendela pop-up dengan solusi, silakan kirimkan log ke pusat dukungan.";
	}

	@Override
	public String irAModoNormalEstiloTL() {
		return "Pergi ke mode normal";
	}

	@Override
	public String noHayEnlacesParaCopiar() {
		return "Tidak ada tautan untuk disalin.";
	}

	@Override
	public String error_inesperado() {
		return "Kesalahan tak terduga";
	}

	@Override
	public String centroDeSoporte() {
		return "Pusat dukungan";
	}

	@Override
	public String noHayCentroSoporteConfigurado() {
		return "Tidak ada pusat dukungan yang dikonfigurasi.";
	}

	// Indonesian (Bahasa Indonesia)
	public String historialMCLogs() {
		return "Riwayat MCLogs";
	}

	public String endpoint() {
		return "Titik Akhir (Endpoint)";
	}

	public String slug() {
		return "Slug";
	}

	public String tokenEliminacion() {
		return "Token penghapusan";
	}

	public String enlace() {
		return "Tautan";
	}

	public String lineas() {
		return "Baris";
	}

	public String errores() {
		return "Kesalahan";
	}

	public String eliminarRegistroMCLogs() {
		return "Hapus catatan";
	}

	public String faltanDatosParaEliminarMCLogs() {
		return "Slug atau token penghapusan tidak ada.";
	}

	public String confirmarEliminarMCLogs() {
		return "Apakah Anda yakin ingin menghapus catatan MCLogs ini?";
	}

	public String registroEliminadoMCLogs() {
		return "Catatan berhasil dihapus.";
	}

	public String confirmar() {
		return "Konfirmasi";
	}

	public String colorCampoTexto() {
		return "Warna bidang teks";
	}

	// Indonesian (Bahasa Indonesia)
	public String historialCDPaste() {
		return "Riwayat CDPaste";
	}

	public String enlaceRaw() {
		return "Tautan raw";
	}

	public String tamano() {
		return "Ukuran";
	}

	public String eliminarRegistroCDPaste() {
		return "Hapus catatan CDPaste";
	}

	public String faltanDatosParaEliminarCDPaste() {
		return "Identifikasi (slug) catatan CDPaste tidak ada.";
	}

	public String confirmarEliminarCDPaste() {
		return "Apakah Anda yakin ingin menghapus catatan CDPaste ini?";
	}

	public String registroEliminadoCDPaste() {
		return "Catatan CDPaste berhasil dihapus.";
	}

	// Indonesian (Bahasa Indonesia)
	public String launcherGenerico() {
		return "Umum";
	}

	public String launcherServidorMinecraft() {
		return "Server Minecraft";
	}

	public String descargandoYPreparandoEnlaces() {
		return "Mengunduh dan menyiapkan tautan...";
	}

	public String seleccioneArchivoLog() {
		return "Pilih file log";
	}

	public String archivoNoValido() {
		return "File tidak valid.";
	}

	public String archivoSeleccionado() {
		return "File dipilih:";
	}

	public String presioneGuardarParaAgregarAnalisis() {
		return "Tekan Simpan dan tutup untuk menambahkannya ke analisis.";
	}

	public String errorAlCargarArchivoArrastrado() {
		return "Kesalahan saat memuat file yang diseret";
	}

	public String errorAlAbrirArchivo() {
		return "Kesalahan saat membuka file";
	}

	public String errorDosPuntos() {
		return "Kesalahan";
	}

	// Indonesian (Bahasa Indonesia)
	public String eliminarRegistros() {
		return "Hapus catatan";
	}

	// Indonesian (Bahasa Indonesia)
	public String editorConfigsMods() {
		return "Editor Konfigurasi Mod";
	}

	public String abrirConfig() {
		return "Buka Config";
	}

	public String guardarConfig() {
		return "Simpan Config";
	}

	public String recargarConfig() {
		return "Muat Ulang";
	}

	public String rutaConfig() {
		return "Jalur";
	}

	public String tipoConfig() {
		return "Tipe";
	}

	public String claveConfig() {
		return "Kunci";
	}

	public String valorConfig() {
		return "Nilai";
	}

	public String buscarConfig() {
		return "Cari";
	}

	public String sinArchivoSeleccionado() {
		return "Tidak ada file yang dipilih";
	}

	public String archivoNoSoportado() {
		return "File tidak didukung oleh mesin apa pun yang tersedia";
	}

	public String configGuardada() {
		return "Konfigurasi berhasil disimpan";
	}

	public String errorCargandoConfig() {
		return "Kesalahan saat memuat konfigurasi";
	}

	public String errorGuardandoConfig() {
		return "Kesalahan saat menyimpan konfigurasi";
	}

	public String seleccionarArchivoConfig() {
		return "Pilih file konfigurasi";
	}

	// Indonesian (Bahasa Indonesia)
	public String suprimirConsolaCD() {
		return "Sembunyikan konsol " + Statics.nombre_cd.obtener();
	}

	public String suprimirVerificacionDeStacktrazos() {
		return "Sembunyikan pemeriksaan stack trace";
	}

	// Indonesian (Bahasa Indonesia)
	public String importadorBotonFusionar() {
		return "Gabungkan";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String mod() {
		return "Mod";
	}

	@Override
	public String version() {
		return "Versi";
	}

	@Override
	public String claseEncontrada() {
		return "Kelas ditemukan";
	}

	@Override
	public String coincidencias() {
		return "Kecocokan";
	}

	@Override
	public String resultadosDeBusqueda() {
		return "Hasil pencarian";
	}

	@Override
	public String desconocido() {
		return "tidak diketahui";
	}

	@Override
	public String desconocida() {
		return "tidak diketahui";
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
		return "Mod ditemukan untuk " + clase;
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String entradaCarpetaInvalida(String ruta) {
		return "Entri folder tidak valid: " + ruta;
	}

	@Override
	public String carpetaSinHashes(String ruta) {
		return "Folder tanpa hash: " + ruta;
	}

	@Override
	public String noSePudoAccederCarpeta(String ruta) {
		return "Tidak dapat mengakses folder: " + ruta;
	}

	@Override
	public String archivoFaltanteEnCarpeta(String ruta, String subRuta) {
		return "File hilang di folder: " + ruta + "/" + subRuta;
	}

	@Override
	public String hashIncorrectoEn(String ruta, String subRuta) {
		return "Hash tidak benar di: " + ruta + "/" + subRuta;
	}

	@Override
	public String archivoNoAutorizadoEnCarpeta(String ruta, String subRuta) {
		return "File tidak diizinkan di folder: " + ruta + "/" + subRuta;
	}

	@Override
	public String entradaArchivoInvalida(String ruta) {
		return "Entri file tidak valid: " + ruta;
	}

	@Override
	public String hashFaltanteParaArchivo(String ruta) {
		return "Hash hilang untuk file: " + ruta;
	}

	@Override
	public String archivoFaltante(String ruta) {
		return "File hilang: " + ruta;
	}

	@Override
	public String errorAlLeerArchivo(String ruta) {
		return "Kesalahan saat membaca file: " + ruta;
	}

	@Override
	public String hashIncorrectoParaArchivo(String ruta) {
		return "Hash tidak benar untuk file: " + ruta;
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String listo() {
		return "Selesai!";
	}

	public String error_al_cargar_plantilla_desde_disco() {
		return "Kesalahan saat memuat templat dari disk: ";
	}

	public String no_se_encontro_plantilla_restablecer() {
		return "Templat tidak ditemukan. Atur ulang menggunakan tombol 'Atur Ulang Templat'.";
	}

	public String plantilla_guardada_en() {
		return "Templat disimpan di: ";
	}

	public String plantilla_restablecida_correctamente() {
		return "Templat berhasil diatur ulang.";
	}

	public String error_al_guardar() {
		return "Kesalahan saat menyimpan: ";
	}

	public String error_al_restablecer() {
		return "Kesalahan saat mengatur ulang: ";
	}

	public String error_al_restablecer_imagen() {
		return "Kesalahan saat mengatur ulang gambar: ";
	}

	public String no_se_encontro_imagen_en_recurso() {
		return "Gambar tidak ditemukan dalam sumber daya: ";
	}

	public String imagen_restablecida() {
		return "Gambar diatur ulang: ";
	}

	public String editor_html() {
		return "Editor HTML";
	}

	public String vista_previa() {
		return "Pratinjau";
	}

	public String configuracion_colores_imagenes() {
		return "Konfigurasi Warna dan Gambar";
	}

	public String imagenes_con_ruta(String ruta) {
		return "Gambar (" + ruta + ")";
	}

	public String enlaces_imagenes_reportes_compartidos() {
		return "Tautan gambar (laporan bersama)";
	}

	public String enlaces_imagenes_reporte_compartido() {
		return "Tautan gambar (laporan bersama)";
	}

	public String url_usada_en_reportes_compartidos() {
		return "URL yang digunakan dalam laporan bersama";
	}

	public String error_creando_codice_json() {
		return "Kesalahan saat membuat codice.json: ";
	}

	public String error_exportando() {
		return "Kesalahan saat mengekspor: ";
	}

	public String validacion() {
		return "Validasi";
	}

	public String ver_codigo() {
		return "Lihat Kode";
	}

	// Indonesian (Bahasa Indonesia)
	public String importar_instancia() {
		return "Impor instansi";
	}

	public String compartir_instancia() {
		return "Bagikan instansi";
	}

	public String error_al_cargar_mods() {
		return "Kesalahan saat memuat mod.";
	}

	public String instalar() {
		return "Instal";
	}

	public String mods_instalados() {
		return "Mod Terinstal";
	}

	public String guardar_como_archivo() {
		return "Simpan sebagai file";
	}

	public String exportando_modpack() {
		return "Mengekspor modpack...";
	}

	public String modpack_exportado() {
		return "Modpack diekspor:\n";
	}

	public String conectando() {
		return "Menghubungkan...";
	}

	public String esperando_descarga() {
		return "Menunggu unduhan...";
	}

	public String finalizado() {
		return "Selesai";
	}

	public String retener_dos_puntos() {
		return "Pertahankan:";
	}

	public String descargar_deps() {
		return "Unduh dependensi";
	}

	public String no_faltan_dependencias() {
		return "Tidak ada dependensi yang hilang.";
	}

	public String descargar_nbt_para_quests() {
		return "Unduh NBT untuk quests";
	}

	public String descargar_nbt() {
		return "Unduh NBT";
	}

	public String error_cargando_informe() {
		return "Kesalahan saat memuat laporan: ";
	}

	@Override
	public String exportar_modpack() {
		return "Ekspor modpack";
	}

	@Override
	public String error_exportando_modpack() {
		return "Kesalahan saat mengekspor modpack:\n";
	}

	@Override
	public String importador_confirmar_descargar_nbt_para_quests() {
		return "Dependensi NBT yang diperlukan untuk menggabungkan quests akan diunduh.\n\n"
				+ "Setelah itu, mungkin perlu restart agar masuk ke classpath.";
	}

	@Override
	public String resultado_nulo() {
		return "Hasil null.";
	}

	@Override
	public String dependencia_nbt_descargada_reiniciar(String nombrePrograma) {
		return "Dependensi NBT telah diunduh.\n\n" + "Restart " + nombrePrograma
				+ " jika penggabungan SNBT masih menyatakan bahwa mesin NBT hilang.";
	}

	@Override
	public String no_se_pudo_descargar_dependencia_nbt() {
		return "Gagal mengunduh dependensi NBT.";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String profilerTituloRendimiento() {
		return "Profiler Kinerja";
	}

	@Override
	public String profilerEstadoActivo() {
		return "Aktif";
	}

	@Override
	public String profilerAyudaMinaly() {
		return "Metode paling lambat muncul di atas. Bilah menunjukkan bobot relatif dari waktu yang terakumulasi.";
	}

	@Override
	public String profilerConfigColorPanel() {
		return "Warna panel";
	}

	@Override
	public String profilerConfigColorBarra() {
		return "Warna bilah";
	}

	@Override
	public String profilerConfigUsarModeloOriginal() {
		return "Gunakan model asli";
	}

	@Override
	public String profilerColumnaClase() {
		return "Kelas";
	}

	@Override
	public String profilerColumnaMetodo() {
		return "Metode";
	}

	@Override
	public String profilerColumnaLlamadas() {
		return "Panggilan";
	}

	@Override
	public String profilerColumnaTiempoTotal() {
		return "Waktu total";
	}

	@Override
	public String profilerEstadoResumen(String estado, int metodos, int top, String totalVisible) {
		return estado + " | metode: " + metodos + " | teratas: " + top + " | total terlihat: " + totalVisible;
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String samplerTituloRendimiento() {
		return "Sampler Kinerja";
	}

	@Override
	public String samplerAyudaEineLotta() {
		return "Metode dengan waktu terakumulasi paling banyak muncul di atas. Bilah menunjukkan secara visual bobot relatif.";
	}

	@Override
	public String samplerColumnaMuestras() {
		return "Sampel";
	}

	@Override
	public String samplerColumnaPromedio() {
		return "Rata-rata";
	}

	@Override
	public String samplerEstadoResumen(String estado, int metodos, int top) {
		return estado + " | metode: " + metodos + " | teratas: " + top;
	}

	// Indonesian (Bahasa Indonesia)
	public String mostrarSelectorAplicacionPrincipal() {
		return "Pemilih aplikasi di GUI utama";
	}

	public String mostrarBotonCDLauncherPrincipal() {
		return "Tombol CDLauncher di GUI utama";
	}

	public String mostrarBotonCDModsPrincipal() {
		return "Tombol CDMods di GUI utama";
	}

	public String mostrarBotonIAPrincipal() {
		return "Tombol AI di GUI utama";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String modsInstalados() {
		return "Mod Terinstal";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String migradorLegacyBotonSidebar() {
		return "Alat Migrasi";
	}

	@Override
	public String migradorLegacyTitulo() {
		return "Alat Migrasi Analisis Legacy";
	}

	@Override
	public String migradorLegacyDescripcion() {
		return "Wizard ini membantu memindahkan konfigurasi dari analisis crash legacy dan berpemilik ke sistem yang modern, terbuka, dan dapat dipelihara. Memigrasikan aturan-aturan ini mengurangi ketergantungan pada alat tertutup dan memudahkan audit, kolaborasi, dan dukungan komunitas. Logo yang ditampilkan di sini bukan logo asli; melainkan parodi yang kami anggap lucu.";
	}

	@Override
	public String migradorLegacyCrashAssistant() {
		return "Wizard Crash Assistant";
	}

	@Override
	public String migradorCrashAssistantDescripcion() {
		return "Mengimpor problematic_mods.json dari Crash Assistant dan menggabungkannya dengan format CrashDetector melalui wizard.";
	}

	@Override
	public String migradorEjecutar() {
		return "Jalankan wizard";
	}

	@Override
	public String migradorCompletado() {
		return "Wizard selesai.";
	}

	@Override
	public String migradorNadaParaMigrar() {
		return "Tidak ditemukan apa pun untuk dimigrasikan.";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String migradorCAUsarPrimitiva() {
		return "Gunakan GUI utama primitif";
	}

	@Override
	public String migradorCADeshabilitarChecks() {
		return "Nonaktifkan centang";
	}

	@Override
	public String migradorCAAvisoUrlSoporteWysiwyg() {
		return "Tautan dukungan tidak dimigrasikan karena Anda tidak menggunakan GUI primitif. Untuk mengubah URL dukungan, gunakan editor WYSIWYG di pengaturan CrashDetector.";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String migradorCAAvisoNoMigrado() {
		return "Peringatan: karena perbedaan antara jenis konfigurasi, daftar tolak verifikasi tidak dapat dimigrasikan secara otomatis. Dari sana, Anda juga dapat menonaktifkan peringatan Intel dan Dif di Daftar Mod; seperti di CrashDetector, ini dianggap sebagai pemeriksaan normal. Jika Anda tidak mengaktifkan mode primitif, Anda harus menggunakan editor HTML WYSIWYG di pengaturan CrashDetector untuk menyisipkan tautan. Jika Anda menggunakan mode primitif, tautan bantuan akan disalin secara otomatis dari Crash Assistant. Untuk mengubah warna dan pengaturan dalam antarmuka pengguna grafis (GUI), Anda harus menuju ke pengaturan CrashDetectors, mengakses area CDSkinCape/GUI Editor dan memilih jenis GUI, serta implementasi spesifiknya. Gambar dapat diedit dari lokasi berikut: "
				+ Statics.carpeta.resolve("imagenes").toString()
				+ ". Skrip JEXL juga tidak akan dimigrasikan. Jika Anda hanya perlu melakukan analisis dasar melalui pencocokan string atau ekspresi reguler, Anda dapat menggunakan fungsi Tambahkan Alasan dalam pengaturan perusahaan; jika Anda memerlukan fungsionalitas yang lebih canggih, Anda harus membuat ekstensi dalam Java: https://github.com/FeatureCreepEAP/crashdetector-tutorial-extention-english";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String centroSoporteTituloCrash() {
		return "Aplikasi tertutup secara tidak terduga.";
	}

	@Override
	public String centroSoporteTextoSuperior() {
		return "Antarmuka grafis ini tidak direkomendasikan untuk pengguna tingkat lanjut (DIY); jika Anda adalah pengguna tingkat lanjut, silakan pergi ke File -> Pengaturan -> Pengaturan "
				+ Statics.nombre_cd.obtener()
				+ " dan ubah Antarmuka Utama dari \"pusat dukungan\" menjadi \"gaya peluncur\". Jika Anda adalah penulis modpack, Anda dapat mengubah teks ini di sana.";
	}

	@Override
	public String centroSoporteTextoBajoTitulo() {
		return "Berikan detail tentang apa yang terjadi sebelum kegagalan.";
	}

	@Override
	public String centroSoporteTextoAviso() {
		return "Baca teks di atas dengan cermat. Tangkapan layar dari jendela ini tidak mengandung informasi yang cukup.";
	}

	@Override
	public String centroSoporteArchivosDisponibles() {
		return "File log yang tersedia";
	}

	@Override
	public String centroSoporteSubirTodoYCopiar() {
		return "Unggah semua dan salin pesan dengan tautan";
	}

	@Override
	public String centroSoportePedirAyuda() {
		return "Minta bantuan di pusat dukungan";
	}

	@Override
	public String mostrarEnExplorador() {
		return "Tampilkan di explorer";
	}

	@Override
	public String subirYCopiarEnlace() {
		return "Unggah dan salin tautan";
	}

	@Override
	public String salir() {
		return "Keluar";
	}

	@Override
	public String colorTextoAviso() {
		return "Warna teks peringatan";
	}

	@Override
	public String colorBotonPrincipal() {
		return "Warna tombol utama";
	}

	@Override
	public String colorTextoBotonPrincipal() {
		return "Warna teks tombol utama";
	}

	@Override
	public String anchoVentana() {
		return "Lebar jendela";
	}

	@Override
	public String altoVentana() {
		return "Tinggi jendela";
	}

	@Override
	public String textoSuperiorPersonalizado() {
		return "Teks atas kustom";
	}

	@Override
	public String textoAvisoPersonalizado() {
		return "Teks peringatan kustom";
	}

	@Override
	public String textoBajoTituloPersonalizado() {
		return "Teks kustom di bawah judul";
	}

	@Override
	public String urlSoporte() {
		return "URL dukungan";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String leyProteccionDatosPersonales() {
		return "Undang-undang perlindungan data pribadi";
	}

	@Override
	public String altoFilaLog() {
		return "Tinggi baris log";
	}

	@Override
	public String anchoNombreArchivo() {
		return "Lebar nama file";
	}

	@Override
	public String anchoBotonAbrir() {
		return "Lebar tombol buka";
	}

	@Override
	public String anchoBotonExplorador() {
		return "Lebar tombol tampilkan di explorer";
	}

	@Override
	public String anchoBotonSubir() {
		return "Lebar tombol unggah";
	}

	@Override
	public String tamanoFuenteBotonPrincipal() {
		return "Ukuran font tombol utama";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String formatoBloqueLogs() {
		return "Format blok log";
	}

	@Override
	public String formatoHeaderMensaje() {
		return "Format header pesan";
	}

	@Override
	public String formatoEstructuraMensaje() {
		return "Format struktur pesan";
	}

	@Override
	public String formatoLineaLog() {
		return "Format baris log";
	}

	@Override
	public String formatoSeparadorLogs() {
		return "Pemisah antar log";
	}

	@Override
	public String formatoModlistDiff() {
		return "Format perbedaan daftar mod";
	}

	@Override
	public String ocultarTextoAviso() {
		return "Sembunyikan teks peringatan";
	}

	@Override
	public String mostrarLogoCuadrado() {
		return "Tampilkan logo persegi";
	}

	@Override
	public String rutaLogoCuadrado() {
		return "Jalur logo persegi";
	}

	@Override
	public String tamanoLogoCuadrado() {
		return "Ukuran logo persegi";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String migradorCAModoPrincipalAviso() {
		return "Pilih cara memigrasikan GUI utama. \"centro_soporte\" adalah yang paling mirip dengan GUI default Crash Assistant, tetapi tidak direkomendasikan, terutama untuk pemain DIY, karena memiliki \"popup hell\" dan desain yang buruk; ini hanya ada untuk membantu memigrasikan lingkungan legacy Crash Assistant. \"primitiva\" adalah GUI yang sangat sederhana yang hanya digunakan untuk berbagi; hanya disarankan untuk modpack, bukan pengguna DIY, dan bahkan jika Anda menonaktifkan semua pemeriksaan yang tidak perlu, ini juga menderita \"popup hell\". \"original\" mempertahankan GUI utama Anda saat ini dan umumnya merupakan opsi yang direkomendasikan untuk sebagian besar pengguna.";
	}

	@Override
	public String migradorCASitioLoggingAviso() {
		return "Pilih apakah Anda ingin memigrasikan situs logging dari Crash Assistant atau mempertahankan situs saat ini dari CrashDetector. Crash Assistant sering menggunakan gnomebot.dev, yang tidak direkomendasikan dibandingkan dengan SecureLogger, PastesDev, atau CDPaste. Untuk sebagian besar pengguna, disarankan untuk mempertahankan situs saat ini.";
	}

	// Indonesian (Bahasa Indonesia)
	@Override
	public String ideScriptTitulo() {
		return "IDE Skrip";
	}

	@Override
	public String ideScriptBotonSidebar() {
		return "Skrip";
	}

	@Override
	public String ideScriptProyecto() {
		return "Proyek:";
	}

	@Override
	public String ideScriptNuevo() {
		return "Baru";
	}

	@Override
	public String ideScriptAbrirCarpeta() {
		return "Buka folder";
	}

	@Override
	public String ideScriptAbrirArchivo() {
		return "Buka file";
	}

	@Override
	public String ideScriptGuardarComo() {
		return "Simpan sebagai";
	}

	@Override
	public String ideScriptDescargarDeps() {
		return "Unduh dependensi";
	}

	@Override
	public String ideScriptCompletar() {
		return "IntelliSense";
	}

	@Override
	public String ideScriptExplorador() {
		return "Penjelajah proyek";
	}

	@Override
	public String ideScriptSinArchivo() {
		return "tanpa file";
	}

	@Override
	public String ideScriptEstado(String proyecto, String archivo) {
		return proyecto + " | " + archivo;
	}

	@Override
	public String ideScriptProyectoDeshabilitadoAviso() {
		return "Jenis proyek ini sudah ada di GUI, tetapi dinonaktifkan sampai server bahasa atau analisnya ditambahkan.";
	}

	@Override
	public String ideScriptDeshabilitadoCorto() {
		return "(dinonaktifkan)";
	}

	@Override
	public String ideScriptNoFaltanDependencias() {
		return "Tidak ada dependensi yang hilang.";
	}

	@Override
	public String ideScriptConfirmarDescargaDeps(int cantidad, String lista) {
		return cantidad + " dependensi akan diunduh untuk IDE skrip:\n\n" + lista
				+ "\nSetelah itu, mungkin perlu restart agar masuk ke classpath.";
	}

	@Override
	public String ideScriptDepsDescargadas(String mensaje) {
		return "Hasil unduhan:\n\n" + mensaje + "\n\nRestart program jika kelas masih belum muncul di classpath.";
	}

	@Override
	public String ideScriptErrorAbrirArchivo() {
		return "Kesalahan membuka file";
	}

	@Override
	public String ideScriptErrorGuardarArchivo() {
		return "Kesalahan menyimpan file";
	}

	@Override
	public String ideScriptColorEditor() {
		return "Warna editor";
	}

	@Override
	public String ideScriptColorKeyword() {
		return "Warna kata kunci";
	}

	@Override
	public String ideScriptColorComentario() {
		return "Warna komentar";
	}

	@Override
	public String ideScriptColorCadena() {
		return "Warna string";
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

}
