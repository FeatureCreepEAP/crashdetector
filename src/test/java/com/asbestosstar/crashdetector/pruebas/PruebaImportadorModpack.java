package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.curseforge.ImportadorModpackCurseForge;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;
import com.asbestosstar.crashdetector.dto.modpack.modrinth.ImportadorModpackModrinth;

/**
 * Pruebas del importador de modpacks para CurseForge y Modrinth.
 * Descarga los modpacks de ejemplo si no existen localmente.
 */
class PruebaImportadorModpack {

	private static final String RECURSOS_MODPACKS =
			"src/test/resources/modpacks";

	private static final String CURSEFORGE_URL =
			"https://edge.forgecdn.net/files/8115/010/ClawsOfBerk-[3.1.4].zip";
	private static final String MODRINTH_URL =
			"https://cdn.modrinth.com/data/MQkboJhx/versions/fmAAYjq0/ClawsOfBerk-[3.1.4].mrpack";

	private static final String CURSEFORGE_FILE =
			"ClawsOfBerk-CurseForge.zip";
	private static final String MODRINTH_FILE =
			"ClawsOfBerk-Modrinth.mrpack";

	private static Path carpetaRecursos;
	private static Path archivoCurseForge;
	private static Path archivoModrinth;

	@TempDir
	static Path tempDir;

	@BeforeAll
	static void descargarModpacks() throws IOException {
		carpetaRecursos = Paths.get(RECURSOS_MODPACKS).toAbsolutePath().normalize();
		archivoCurseForge = carpetaRecursos.resolve(CURSEFORGE_FILE);
		archivoModrinth = carpetaRecursos.resolve(MODRINTH_FILE);

		if (!Files.exists(carpetaRecursos)) {
			Files.createDirectories(carpetaRecursos);
		}

		descargarSiNoExiste(archivoCurseForge, CURSEFORGE_URL);
		descargarSiNoExiste(archivoModrinth, MODRINTH_URL);
	}

	private static void descargarSiNoExiste(Path destino, String urlStr) throws IOException {
		if (Files.isRegularFile(destino)) {
			return;
		}

		HttpURLConnection con = null;
		try {
			URL url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(30000);
			con.setReadTimeout(120000);
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int codigo = con.getResponseCode();
			if (codigo < 200 || codigo >= 300) {
				throw new IOException("HTTP " + codigo + " al descargar " + urlStr);
			}

			Files.copy(con.getInputStream(), destino);
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

	@BeforeEach
	void verificarArchivos() {
		assertTrue(Files.isRegularFile(archivoCurseForge),
				"Modpack CurseForge no encontrado: " + archivoCurseForge);
		assertTrue(Files.isRegularFile(archivoModrinth),
				"Modpack Modrinth no encontrado: " + archivoModrinth);
	}

	// ======================== CurseForge ========================

	@Test
	void curseforgePuedeImportarZip() {
		ImportadorModpackCurseForge importador = new ImportadorModpackCurseForge();
		assertTrue(importador.puedeImportar(archivoCurseForge));
		assertTrue(importador.puedeImportar(Paths.get("prueba.zip")));
		assertFalse(importador.puedeImportar(Paths.get("prueba.mrpack")));
		assertFalse(importador.puedeImportar(Paths.get("prueba.jar")));
	}

	@Test
	void curseforgeNombreImportador() {
		ImportadorModpackCurseForge importador = new ImportadorModpackCurseForge();
		assertEquals("CurseForge", importador.obtenerNombreImportador());
		assertEquals("zip", importador.obtenerExtensionModpack());
	}

	@Test
	void curseforgeImportarLanzaExcepcionConArchivoInexistente() {
		ImportadorModpackCurseForge importador = new ImportadorModpackCurseForge();
		Path inexistente = Paths.get("/no/existe/modpack.zip");
		assertThrows(IOException.class, () -> {
			importador.importarModpack(inexistente, tempDir, new PoliticaImportacion());
		});
	}

	@Test
	void curseforgeImportarInstalaOverrides() throws IOException {
		ImportadorModpackCurseForge importador = new ImportadorModpackCurseForge();
		Path destino = tempDir.resolve("curseforge-test");
		Files.createDirectories(destino);

		PoliticaImportacion politica = new PoliticaImportacion();
		politica.reemplazarTodo = true;

		ResultadoImportacion resultado = importador.importarModpack(
				archivoCurseForge, destino, politica);

		// Los overrides deben copiarse
		assertTrue(resultado.copiados > 0,
				"Se esperaba que se copiaran archivos de overrides, pero se copiaron " + resultado.copiados);

		// Verificar que al menos un archivo de config existe
		Path configDir = destino.resolve("config");
		assertTrue(Files.exists(configDir), "La carpeta config no se creo en " + destino);
		assertTrue(Files.isDirectory(configDir), "config no es un directorio");

		// Verificar archivos especificos de overrides
		assertTrue(Files.exists(configDir.resolve("advancementplaques-common.toml")),
				"advancementplaques-common.toml no se instalo");
		assertTrue(Files.exists(configDir.resolve("aiimprovements-common.toml")),
				"aiimprovements-common.toml no se instalo");
	}

	@Test
	void curseforgeManifestContieneArchivos() throws IOException {
		// Verificar que el manifest.json se puede leer y tiene archivos
		ImportadorModpackCurseForge importador = new ImportadorModpackCurseForge();
		Path destino = tempDir.resolve("curseforge-manifest-test");
		Files.createDirectories(destino);

		PoliticaImportacion politica = new PoliticaImportacion();
		politica.reemplazarTodo = true;

		ResultadoImportacion resultado = importador.importarModpack(
				archivoCurseForge, destino, politica);

		// No deberia haber errores en la instalacion de overrides
		assertEquals(0, resultado.errores,
				"Errores en la importacion: " + resultado.mensajes);
	}

	@Test
	void curseforgeManifestParseaCorrectamente() throws IOException {
		// Extraer y verificar el manifest.json directamente
		String manifestJson = extraerDesdeZip(archivoCurseForge, "manifest.json");
		assertNotNull(manifestJson);
		assertTrue(manifestJson.contains("minecraftModpack"),
				"El manifest no contiene manifestType minecraftModpack");

		Json.Nodo manifest = Json.leer(manifestJson);
		assertEquals("minecraftModpack", manifest.obtener("manifestType").comoCadena());
		assertEquals(1, manifest.obtener("manifestVersion").comoEntero());

		Json.Nodo files = manifest.obtener("files");
		assertTrue(files.esArreglo(), "files debe ser un arreglo");
		assertTrue(files.tamano() > 0, "files debe tener al menos un elemento");

		// Verificar la estructura del primer archivo
		Json.Nodo primerArchivo = files.en(0);
		long projectId = primerArchivo.obtener("projectID").comoLargo();
		long fileId = primerArchivo.obtener("fileID").comoLargo();
		assertTrue(projectId > 0, "projectID debe ser positivo: " + projectId);
		assertTrue(fileId > 0, "fileID debe ser positivo: " + fileId);
	}

	// ======================== Modrinth ========================

	@Test
	void modrinthPuedeImportarMrpack() {
		ImportadorModpackModrinth importador = new ImportadorModpackModrinth();
		assertTrue(importador.puedeImportar(archivoModrinth));
		assertTrue(importador.puedeImportar(Paths.get("prueba.mrpack")));
		assertFalse(importador.puedeImportar(Paths.get("prueba.zip")));
		assertFalse(importador.puedeImportar(Paths.get("prueba.jar")));
	}

	@Test
	void modrinthNombreImportador() {
		ImportadorModpackModrinth importador = new ImportadorModpackModrinth();
		assertEquals("Modrinth", importador.obtenerNombreImportador());
		assertEquals("mrpack", importador.obtenerExtensionModpack());
	}

	@Test
	void modrinthImportarLanzaExcepcionConArchivoInexistente() {
		ImportadorModpackModrinth importador = new ImportadorModpackModrinth();
		Path inexistente = Paths.get("/no/existe/modpack.mrpack");
		assertThrows(IOException.class, () -> {
			importador.importarModpack(inexistente, tempDir, new PoliticaImportacion());
		});
	}

	@Test
	void modrinthImportarInstalaOverrides() throws IOException {
		ImportadorModpackModrinth importador = new ImportadorModpackModrinth();
		Path destino = tempDir.resolve("modrinth-test");
		Files.createDirectories(destino);

		PoliticaImportacion politica = new PoliticaImportacion();
		politica.reemplazarTodo = true;

		ResultadoImportacion resultado = importador.importarModpack(
				archivoModrinth, destino, politica);

		// Los overrides deben copiarse
		assertTrue(resultado.copiados > 0,
				"Se esperaba que se copiaran archivos de overrides, pero se copiaron " + resultado.copiados);

		// Verificar que al menos un archivo de config existe
		Path configDir = destino.resolve("config");
		assertTrue(Files.exists(configDir), "La carpeta config no se creo en " + destino);
		assertTrue(Files.isDirectory(configDir), "config no es un directorio");

		// Verificar archivos especificos de overrides
		assertTrue(Files.exists(configDir.resolve("advancementplaques-common.toml")),
				"advancementplaques-common.toml no se instalo");
		assertTrue(Files.exists(configDir.resolve("aiimprovements-common.toml")),
				"aiimprovements-common.toml no se instalo");
	}

	@Test
	void modrinthIndexParseaCorrectamente() throws IOException {
		// Extraer y verificar el modrinth.index.json directamente
		String indexJson = extraerDesdeZip(archivoModrinth, "modrinth.index.json");
		assertNotNull(indexJson);
		assertTrue(indexJson.contains("minecraft"),
				"El index no contiene game minecraft");

		Json.Nodo index = Json.leer(indexJson);
		assertEquals("minecraft", index.obtener("game").comoCadena());
		assertEquals(1, index.obtener("formatVersion").comoEntero());
		assertEquals("COB", index.obtener("name").comoCadena());

		Json.Nodo files = index.obtener("files");
		assertTrue(files.esArreglo(), "files debe ser un arreglo");
		assertTrue(files.tamano() > 0, "files debe tener al menos un elemento");

		// Verificar la estructura del primer archivo
		Json.Nodo primerArchivo = files.en(0);
		String path = primerArchivo.obtener("path").comoCadena();
		assertTrue(path.startsWith("mods/"),
				"El path debe empezar con mods/: " + path);

		// Verificar hashes
		Json.Nodo hashes = primerArchivo.obtener("hashes");
		String sha1 = hashes.obtener("sha1").comoCadena();
		String sha512 = hashes.obtener("sha512").comoCadena();
		assertNotNull(sha1, "sha1 no debe ser nulo");
		assertTrue(sha1.length() == 40, "sha1 debe tener 40 caracteres: " + sha1.length());
		assertNotNull(sha512, "sha512 no debe ser nulo");
		assertTrue(sha512.length() == 128, "sha512 debe tener 128 caracteres: " + sha512.length());

		// Verificar downloads
		Json.Nodo downloads = primerArchivo.obtener("downloads");
		assertTrue(downloads.esArreglo(), "downloads debe ser un arreglo");
		assertTrue(downloads.tamano() > 0, "downloads debe tener al menos una URL");
		String downloadUrl = downloads.en(0).comoCadena();
		assertTrue(downloadUrl.startsWith("https://"),
				"La URL de descarga debe empezar con https://: " + downloadUrl);

		// Verificar fileSize
		long fileSize = primerArchivo.obtener("fileSize").comoLargo();
		assertTrue(fileSize > 0, "fileSize debe ser positivo: " + fileSize);
	}

	@Test
	void modrinthArchivosTienenRutasValidas() throws IOException {
		String indexJson = extraerDesdeZip(archivoModrinth, "modrinth.index.json");
		Json.Nodo index = Json.leer(indexJson);
		Json.Nodo files = index.obtener("files");

		for (int i = 0; i < files.tamano(); i++) {
			Json.Nodo file = files.en(i);
			String path = file.obtener("path").comoCadena();

			// Verificar que no hay rutas inseguras
			assertFalse(path.contains("../"), "Ruta insegura detectada: " + path);
			assertFalse(path.startsWith("/"), "Ruta absoluta detectada: " + path);
			assertFalse(path.equals(".."), "Ruta '..' detectada");

			// Verificar que los mods estan en la carpeta correcta
			if (path.endsWith(".jar")) {
				assertTrue(path.startsWith("mods/"),
						"Mod JAR debe estar en mods/: " + path);
			}
		}
	}

	@Test
	void modrinthImportarNoTieneErroresEnOverrides() throws IOException {
		ImportadorModpackModrinth importador = new ImportadorModpackModrinth();
		Path destino = tempDir.resolve("modrinth-override-test");
		Files.createDirectories(destino);

		PoliticaImportacion politica = new PoliticaImportacion();
		politica.reemplazarTodo = true;

		ResultadoImportacion resultado = importador.importarModpack(
				archivoModrinth, destino, politica);

		// No deberia haber errores en la instalacion de overrides
		assertEquals(0, resultado.errores,
				"Errores en la importacion: " + resultado.mensajes);
	}

	// ======================== Comparacion ========================

	@Test
	void ambosImportadoresInstalanMismosOverrides() throws IOException {
		// Instalar overrides de ambos modpacks y comparar
		Path destinoCF = tempDir.resolve("cf-overrides");
		Path destinoMR = tempDir.resolve("mr-overrides");
		Files.createDirectories(destinoCF);
		Files.createDirectories(destinoMR);

		PoliticaImportacion politica = new PoliticaImportacion();
		politica.reemplazarTodo = true;

		new ImportadorModpackCurseForge().importarModpack(archivoCurseForge, destinoCF, politica);
		new ImportadorModpackModrinth().importarModpack(archivoModrinth, destinoMR, politica);

		// Ambos deben tener la carpeta config
		assertTrue(Files.exists(destinoCF.resolve("config")));
		assertTrue(Files.exists(destinoMR.resolve("config")));

		// Comparar algunos archivos comunes
		String[] archivosComunes = {
			"config/advancementplaques-common.toml",
			"config/aiimprovements-common.toml",
			"config/artifacts-common.toml"
		};

		for (String archivo : archivosComunes) {
			Path cfArchivo = destinoCF.resolve(archivo);
			Path mrArchivo = destinoMR.resolve(archivo);

			if (Files.exists(cfArchivo) && Files.exists(mrArchivo)) {
				// Los archivos deben ser iguales (mismo contenido)
				assertEquals(Files.size(cfArchivo), Files.size(mrArchivo),
						"Tamaño diferente para " + archivo);
			}
		}
	}

	@Test
	void curseForgeYModrinthTienenMismoNombreDePack() throws IOException {
		String manifestJson = extraerDesdeZip(archivoCurseForge, "manifest.json");
		String indexJson = extraerDesdeZip(archivoModrinth, "modrinth.index.json");

		Json.Nodo manifest = Json.leer(manifestJson);
		Json.Nodo index = Json.leer(indexJson);

		String nombreCF = manifest.obtener("name").comoCadena();
		String nombreMR = index.obtener("name").comoCadena();

		assertEquals(nombreCF, nombreMR,
				"Los nombres del modpack deben coincidir: CF='" + nombreCF + "', MR='" + nombreMR + "'");
	}

	// ======================== Utilidades ========================

	private static String extraerDesdeZip(Path zip, String entrada) throws IOException {
		java.util.zip.ZipFile zf = new java.util.zip.ZipFile(zip.toFile());
		try {
			for (java.util.Enumeration<? extends java.util.zip.ZipEntry> e = zf.entries(); e.hasMoreElements();) {
				java.util.zip.ZipEntry entry = e.nextElement();
				if (entry.getName().equalsIgnoreCase(entrada) && !entry.isDirectory()) {
					byte[] bytes = new byte[(int) entry.getSize()];
					try (java.io.InputStream is = zf.getInputStream(entry)) {
						int offset = 0;
						int read;
						while (offset < bytes.length && (read = is.read(bytes, offset, bytes.length - offset)) != -1) {
							offset += read;
						}
						return new String(bytes, 0, offset, java.nio.charset.StandardCharsets.UTF_8);
					}
				}
			}
		} finally {
			zf.close();
		}
		return null;
	}
}
