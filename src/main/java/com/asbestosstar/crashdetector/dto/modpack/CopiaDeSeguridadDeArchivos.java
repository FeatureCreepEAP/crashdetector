package com.asbestosstar.crashdetector.dto.modpack;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.config.ConfigDouble;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.gui.tipos.modapi.PanelAPIBase;

public class CopiaDeSeguridadDeArchivos {

	public static final String CARPETA_AUTO_BACKUP = "MODPACK_AUTO_BACKUP";
	public static final String CARPETA_MUNDOS_AUTO_BACKUP = "MODPACK_WORLD_AUTO_BACKUP";

	public static final String FRECUENCIA_UNA_VEZ_POR_DIA = "una_vez_por_dia";
	public static final String FRECUENCIA_TODAS_LAS_VECES = "todas_las_veces";

	public static ConfigBoolean autoBackupActivado = ConfigBoolean.de("modpack.auto_backup.activado", false);
	public static ConfigDouble autoBackupDiasConservar = ConfigDouble.de("modpack.auto_backup.dias_conservar", 30.0);
	public static ConfigDouble autoBackupTamanoMaximoMB = ConfigDouble.de("modpack.auto_backup.tamano_maximo_mb",
			2000.0);
	public static ConfigString autoBackupFrecuencia = ConfigString.de("modpack.auto_backup.frecuencia",
			FRECUENCIA_UNA_VEZ_POR_DIA);

	private static final ExecutorService EJECUTOR_BACKUP = Executors.newSingleThreadExecutor(new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r, Statics.nombre_cd.obtener() + "-AutoBackup-Modpack");
			t.setDaemon(true);
			return t;
		}
	});

	public static Path obtenerCarpetaDeCopiasDeSeguridad() {
		String sistema = System.getProperty("os.name", "").toLowerCase();
		String usuario = System.getProperty("user.home");

		if (sistema.contains("win")) {
			String appData = System.getenv("APPDATA");

			if (appData != null && !appData.trim().isEmpty()) {
				return Paths.get(appData, ".minecraft", "backup");
			}

			return Paths.get(usuario, "AppData", "Roaming", ".minecraft", "backup");
		}

		if (sistema.contains("mac")) {
			return Paths.get(usuario, "Library", "Application Support", "minecraft", "backup");
		}

		return Paths.get(usuario, ".minecraft", "backup");
	}

	public static Path obtenerCarpetaDeBackupsModpack() {
		return obtenerCarpetaDeCopiasDeSeguridad().resolve("modpacks");
	}

	public static Path obtenerCarpetaDeAutoBackup() {
		return obtenerCarpetaDeBackupsModpack().resolve(CARPETA_AUTO_BACKUP);
	}

	public static Path obtenerCarpetaDeAutoBackupMundos() {
		return obtenerCarpetaDeCopiasDeSeguridad().resolve("worlds").resolve(CARPETA_MUNDOS_AUTO_BACKUP);
	}

	public static void hacerAutoBackupSiCorresponde() {
		Path rutaInstancia = Statics.CARPETA_DE_APP.toPath().toAbsolutePath().normalize();

		if (!debeHacerAutoBackup(rutaInstancia)) {
			return;
		}

		List<Path> rutasEntrada = obtenerRutasAutoBackupModpack(rutaInstancia);
		hacerAutoBackupSiCorresponde(rutaInstancia, rutasEntrada);
	}

	public static void hacerAutoBackupSiCorresponde(final Path rutaInstancia, final List<Path> rutasEntrada) {
		final Path rutaNormal = rutaInstancia == null ? Statics.CARPETA_DE_APP.toPath().toAbsolutePath().normalize()
				: rutaInstancia.toAbsolutePath().normalize();

		if (!debeHacerAutoBackup(rutaNormal)) {
			return;
		}

		EJECUTOR_BACKUP.submit(new Runnable() {
			@Override
			public void run() {
				hacerAutoBackupSiCorrespondeSincrono(rutaNormal, rutasEntrada);
			}
		});
	}

	private static boolean debeHacerAutoBackup(Path rutaInstancia) {
		try {
			if (!autoBackupActivado.obtener()) {
				return false;
			}

			String frecuencia = autoBackupFrecuencia.obtener();
			if (frecuencia == null || frecuencia.trim().isEmpty()) {
				frecuencia = FRECUENCIA_UNA_VEZ_POR_DIA;
			}

			if (FRECUENCIA_UNA_VEZ_POR_DIA.equals(frecuencia) && yaExisteAutoBackupDeHoy(rutaInstancia)) {
				return false;
			}

			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	private static void hacerAutoBackupSiCorrespondeSincrono(Path rutaInstancia, List<Path> rutasEntrada) {
		try {

			ProveedorMods proveedorTLMods = buscarProveedorTLMods();
			if (proveedorTLMods == null || !proveedorTLMods.soportaExportarModpack()) {
				CrashDetectorLogger.log("No se pudo crear auto-backup: no existe proveedor TLMods exportable.");
				return;
			}

			List<Path> rutasModpack = obtenerRutasAutoBackupModpack(rutaInstancia);

			String extension = obtenerExtensionProveedor(proveedorTLMods);
			Path destino = crearRutaAutoBackup(rutaInstancia, extension);

			proveedorTLMods.exportarModpack(destino, rutasModpack);

			hacerBackupDeMundos(rutaInstancia);

			limpiarAutoBackupsAntiguos();

			CrashDetectorLogger.log("Auto-backup de modpack creado: " + destino);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public static List<Path> obtenerRutasAutoBackupModpack(Path rutaInstancia) {
		Path base = rutaInstancia == null ? Statics.CARPETA_DE_APP.toPath().toAbsolutePath().normalize()
				: rutaInstancia.toAbsolutePath().normalize();

		List<Path> ret = new ArrayList<Path>();

		agregarSiExiste(ret, base.resolve("mods"));
		agregarSiExiste(ret, base.resolve("config"));
		agregarSiExiste(ret, base.resolve("etc"));
		agregarSiExiste(ret, base.resolve("gfx"));
		agregarSiExiste(ret, base.resolve("history"));
		agregarSiExiste(ret, base.resolve("addons"));
		agregarSiExiste(ret, base.resolve("plugins"));
		agregarSiExiste(ret, base.resolve("datafiedcontents"));
		agregarSiExiste(ret, base.resolve("kubejs"));
		agregarSiExiste(ret, base.resolve("resourcepacks"));
		agregarSiExiste(ret, base.resolve("shaderpacks"));
		agregarSiExiste(ret, base.resolve("datapacks"));
		agregarSiExiste(ret, base.resolve("options.txt"));
		agregarSiExiste(ret, base.resolve("TLauncherAdditional.json"));

		return ret;
	}

	private static void hacerBackupDeMundos(Path rutaInstancia) throws IOException {
		Path base = rutaInstancia == null ? Statics.CARPETA_DE_APP.toPath().toAbsolutePath().normalize()
				: rutaInstancia.toAbsolutePath().normalize();

		Path saves = base.resolve("saves");
		if (!Files.isDirectory(saves)) {
			return;
		}

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(saves)) {
			for (Path mundo : stream) {
				if (!Files.isDirectory(mundo)) {
					continue;
				}

				String nombreMundo = mundo.getFileName().toString();
				Path carpetaDestino = obtenerCarpetaDeAutoBackupMundos().resolve(nombreMundo);
				Files.createDirectories(carpetaDestino);

				String fecha = new SimpleDateFormat("yyyy-MM-dd'T'HH_mm_ss").format(new Date());
				Path destino = carpetaDestino.resolve(limpiarNombreArchivo(nombreMundo) + "_" + fecha + ".zip");

				crearZipDeCarpeta(mundo, destino);
				CrashDetectorLogger.log("Auto-backup de mundo creado: " + destino);
			}
		}
	}

	private static void crearZipDeCarpeta(Path carpeta, Path destino) throws IOException {
		final Path carpetaNormal = carpeta.toAbsolutePath().normalize();
		final String nombreRaiz = carpetaNormal.getFileName().toString();

		if (destino.getParent() != null) {
			Files.createDirectories(destino.getParent());
		}

		try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(destino))) {
			Files.walkFileTree(carpetaNormal, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path archivo, BasicFileAttributes attrs) throws IOException {
					Path relativa = carpetaNormal.relativize(archivo.toAbsolutePath().normalize());
					String entrada = nombreRaiz + "/" + relativa.toString().replace('\\', '/');

					zos.putNextEntry(new ZipEntry(entrada));
					Files.copy(archivo, zos);
					zos.closeEntry();

					return FileVisitResult.CONTINUE;
				}
			});
		}
	}

	private static void agregarSiExiste(List<Path> lista, Path ruta) {
		try {
			if (ruta != null && Files.exists(ruta)) {
				lista.add(ruta.toAbsolutePath().normalize());
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public static Path crearRutaBackup(Path rutaInstancia, String extension) throws IOException {
		Path carpeta = obtenerCarpetaDeBackupsModpack();
		Files.createDirectories(carpeta);
		return carpeta.resolve(crearNombreArchivoBackup(rutaInstancia, extension));
	}

	public static Path crearRutaAutoBackup(Path rutaInstancia, String extension) throws IOException {
		Path carpeta = obtenerCarpetaDeAutoBackup();
		Files.createDirectories(carpeta);
		return carpeta.resolve(crearNombreArchivoBackup(rutaInstancia, extension));
	}

	public static String crearNombreArchivoBackup(Path rutaInstancia, String extension) {
		String nombreInstancia = obtenerNombreInstancia(rutaInstancia);
		String fecha = new SimpleDateFormat("yyyy-MM-dd'T'HH_mm_ss").format(new Date());

		if (extension == null || extension.trim().isEmpty()) {
			extension = "zip";
		}

		extension = extension.trim();
		if (extension.startsWith(".")) {
			extension = extension.substring(1);
		}

		return nombreInstancia + "_" + fecha + "." + extension;
	}

	private static boolean yaExisteAutoBackupDeHoy(Path rutaInstancia) throws IOException {
		Path carpeta = obtenerCarpetaDeAutoBackup();
		if (!Files.isDirectory(carpeta)) {
			return false;
		}

		String nombreInstancia = obtenerNombreInstancia(rutaInstancia);
		String fechaHoy = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		try (java.util.stream.Stream<Path> stream = Files.list(carpeta)) {
			return stream.anyMatch(p -> {
				String n = p.getFileName().toString();
				return n.startsWith(nombreInstancia + "_" + fechaHoy);
			});
		}
	}

	private static ProveedorMods buscarProveedorTLMods() {
		Supplier<ProveedorMods> proveedorTLMods = PanelAPIBase.PROVEEDORES_MODS.get("tlmods");

		if (proveedorTLMods == null) {
			return null;
		}

		ProveedorMods proveedor = proveedorTLMods.get();

		if (proveedor != null && proveedor.soportaExportarModpack()) {
			return proveedor;
		}

		return null;
	}

	public static String obtenerExtensionProveedor(ProveedorMods proveedor) {
		if (proveedor instanceof ProveedorConExtensionModpack) {
			return ((ProveedorConExtensionModpack) proveedor).obtenerExtensionModpack();
		}

		return "zip";
	}

	public static void limpiarAutoBackupsAntiguos() {
		limpiarCarpetaPorEdadYTamano(obtenerCarpetaDeAutoBackup());

		try {
			Path mundos = obtenerCarpetaDeAutoBackupMundos();
			if (Files.isDirectory(mundos)) {
				try (java.util.stream.Stream<Path> stream = Files.walk(mundos)) {
					stream.filter(Files::isRegularFile).forEach(p -> limpiarArchivoSiAntiguo(p));
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private static void limpiarCarpetaPorEdadYTamano(Path carpeta) {
		try {
			if (!Files.isDirectory(carpeta)) {
				return;
			}

			try (java.util.stream.Stream<Path> stream = Files.list(carpeta)) {
				stream.filter(Files::isRegularFile).forEach(p -> limpiarArchivoSiAntiguo(p));
			}

			limpiarPorTamanoMaximo(carpeta);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private static void limpiarArchivoSiAntiguo(Path p) {
		try {
			final long ahora = System.currentTimeMillis();
			final long dias = Math.max(1L, autoBackupDiasConservar.obtener().longValue());
			final long maxEdad = dias * 24L * 60L * 60L * 1000L;

			long edad = ahora - Files.getLastModifiedTime(p).toMillis();
			if (edad > maxEdad) {
				Files.deleteIfExists(p);
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private static void limpiarPorTamanoMaximo(Path carpeta) throws IOException {
		long maxBytes = Math.max(1L, autoBackupTamanoMaximoMB.obtener().longValue()) * 1024L * 1024L;

		List<Path> archivos = new ArrayList<Path>();

		try (java.util.stream.Stream<Path> stream = Files.list(carpeta)) {
			stream.filter(Files::isRegularFile).forEach(archivos::add);
		}

		archivos.sort((a, b) -> {
			try {
				return Files.getLastModifiedTime(a).compareTo(Files.getLastModifiedTime(b));
			} catch (IOException e) {
				return 0;
			}
		});

		long total = 0;
		for (Path p : archivos) {
			total += Files.size(p);
		}

		for (Path p : archivos) {
			if (total <= maxBytes) {
				break;
			}

			long tamano = Files.size(p);
			Files.deleteIfExists(p);
			total -= tamano;
		}
	}

	public static String obtenerNombreInstancia(Path rutaInstancia) {
		if (rutaInstancia == null) {
			return "instancia";
		}

		Path normal = rutaInstancia.toAbsolutePath().normalize();

		for (int i = 0; i < normal.getNameCount(); i++) {
			String parte = normal.getName(i).toString().toLowerCase();

			if (parte.equals("instance") || parte.equals("instances") || parte.equals("modpacks")
					|| parte.equals("versions")) {
				if (i + 1 < normal.getNameCount()) {
					return limpiarNombreArchivo(normal.getName(i + 1).toString());
				}
			}
		}

		Path nombre = normal.getFileName();
		return limpiarNombreArchivo(nombre != null ? nombre.toString() : "instancia");
	}

	private static String limpiarNombreArchivo(String entrada) {
		if (entrada == null || entrada.trim().isEmpty()) {
			return "instancia";
		}

		String normal = Normalizer.normalize(entrada.trim(), Normalizer.Form.NFD);
		normal = normal.replaceAll("[^\\p{ASCII}]", "");
		normal = normal.replaceAll("[\\\\/:*?\"<>|]", "_");
		normal = normal.replaceAll("\\s+", " ").trim();

		return normal.isEmpty() ? "instancia" : normal;
	}
}