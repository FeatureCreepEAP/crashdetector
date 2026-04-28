package com.asbestosstar.crashdetector.dto.modpack.curseforge;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.dto.modpack.importar.ImportadorModpack;
import com.asbestosstar.crashdetector.dto.modpack.importar.InfoEntradaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;
import com.asbestosstar.crashdetector.dto.modpack.tlmods.ActualizadorTLauncherAdditional;
import com.asbestosstar.crashdetector.json.Json;

public class ImportadorModpackCurseForge implements ImportadorModpack {

	private static final String NOMBRE_MANIFEST = "manifest.json";
	private static final String NOMBRE_TLAUNCHER_ADDITIONAL = "TLauncherAdditional.json";

	@Override
	public String obtenerNombreImportador() {
		return "CurseForge";
	}

	@Override
	public String obtenerExtensionModpack() {
		return "zip";
	}

	@Override
	public boolean puedeImportar(Path archivoModpack) {
		return archivoModpack != null && archivoModpack.getFileName() != null
				&& archivoModpack.getFileName().toString().toLowerCase().endsWith(".zip");
	}

	@Override
	public ResultadoImportacion importarModpack(Path archivoModpack, Path carpetaDestino, PoliticaImportacion politica)
			throws IOException {

		return importar(archivoModpack, carpetaDestino, politica, null);
	}

	public ResultadoImportacion importar(Path archivoModpack, Path carpetaDestino, PoliticaImportacion politica,
			ResolutorConflictosImportacion resolutor) throws IOException {

		if (archivoModpack == null || !Files.isRegularFile(archivoModpack)) {
			throw new IOException("El modpack CurseForge no existe: " + archivoModpack);
		}

		if (carpetaDestino == null) {
			carpetaDestino = java.nio.file.Paths.get(".").toAbsolutePath().normalize();
		}

		if (politica == null) {
			politica = new PoliticaImportacion();
		}

		carpetaDestino = carpetaDestino.toAbsolutePath().normalize();

		ResultadoImportacion total = new ResultadoImportacion();

		Map<String, EntradaZipLeida> entradas = leerEntradasZip(archivoModpack);
		Json.Nodo manifest = leerJsonDesdeZip(entradas, NOMBRE_MANIFEST);
		Json.Nodo tla = leerJsonDesdeZip(entradas, NOMBRE_TLAUNCHER_ADDITIONAL);

		if (manifest == null) {
			throw new IOException("El ZIP no contiene manifest.json.");
		}

		mezclar(total, instalarOverrides(entradas, manifest, carpetaDestino, politica));
		mezclar(total, descargarArchivosDesdeManifest(manifest, carpetaDestino, politica));

		if (tla != null) {
			Path archivoTLA = carpetaDestino.resolve(NOMBRE_TLAUNCHER_ADDITIONAL);
			InfoEntradaImportacion info = crearInfoSimple(NOMBRE_TLAUNCHER_ADDITIONAL);

			mezclar(total, instalarEntradaConConflictos(Json.escribir(tla).getBytes(StandardCharsets.UTF_8), info,
					archivoTLA, politica));
		}

		actualizarTLauncherAdditionalDespuesDeImportar(carpetaDestino);

		return total;
	}

	private ResultadoImportacion instalarOverrides(Map<String, EntradaZipLeida> entradas, Json.Nodo manifest,
			Path carpetaDestino, PoliticaImportacion politica) throws IOException {

		ResultadoImportacion total = new ResultadoImportacion();

		String carpetaOverrides = obtenerCadenaSeguro(manifest.obtener("overrides"), "overrides");
		if (carpetaOverrides == null || carpetaOverrides.trim().isEmpty()) {
			carpetaOverrides = "overrides";
		}

		carpetaOverrides = carpetaOverrides.replace('\\', '/');

		while (carpetaOverrides.startsWith("/")) {
			carpetaOverrides = carpetaOverrides.substring(1);
		}

		while (carpetaOverrides.endsWith("/")) {
			carpetaOverrides = carpetaOverrides.substring(0, carpetaOverrides.length() - 1);
		}

		String prefijo = carpetaOverrides + "/";

		for (EntradaZipLeida entrada : entradas.values()) {
			String nombre = entrada.nombre.replace('\\', '/');

			if (!nombre.startsWith(prefijo)) {
				continue;
			}

			String rutaRelativa = nombre.substring(prefijo.length());

			if (rutaRelativa.trim().isEmpty()) {
				continue;
			}

			if (!rutaSegura(rutaRelativa)) {
				total.saltados++;
				total.mensajes.add("Ruta insegura omitida: " + nombre);
				continue;
			}

			Path destino = carpetaDestino.resolve(rutaRelativa).normalize();

			if (!destino.toAbsolutePath().normalize().startsWith(carpetaDestino)) {
				total.saltados++;
				total.mensajes.add("Ruta intenta salir de la carpeta destino: " + nombre);
				continue;
			}

			InfoEntradaImportacion info = crearInfoSimple(rutaRelativa);
			info.nombreEntrada = nombre;
			info.fechaModificacion = entrada.fechaModificacion;
			info.tamanoDeclarado = entrada.bytes.length;
			info.esMod = rutaRelativa.startsWith("mods/");

			mezclar(total, instalarEntradaConConflictos(entrada.bytes, info, destino, politica));
		}

		return total;
	}

	private ResultadoImportacion descargarArchivosDesdeManifest(Json.Nodo manifest, Path carpetaDestino,
			PoliticaImportacion politica) throws IOException {

		ResultadoImportacion total = new ResultadoImportacion();

		Json.Nodo files = manifest.obtener("files");
		if (files == null || !files.esArreglo()) {
			return total;
		}

		for (int i = 0; i < files.tamano(); i++) {
			Json.Nodo f = files.en(i);

			long projectId = obtenerLargoSeguro(f.obtener("projectID"), 0L);
			long fileId = obtenerLargoSeguro(f.obtener("fileID"), 0L);

			if (projectId <= 0L || fileId <= 0L) {
				continue;
			}

			try {
				Json.Nodo modCF = solicitarModCurseForge(projectId);
				Json.Nodo archivoCF = solicitarArchivoCurseForge(projectId, fileId);

				Json.Nodo dataArchivo = archivoCF.obtener("data");

				String nombreArchivo = obtenerCadenaSeguro(dataArchivo.obtener("fileName"), "");
				String downloadUrl = obtenerCadenaSeguro(dataArchivo.obtener("downloadUrl"), "");

				if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
					total.saltados++;
					total.mensajes.add("Archivo CurseForge sin nombre: " + projectId + "/" + fileId);
					continue;
				}

				if (downloadUrl == null || downloadUrl.trim().isEmpty()) {
					downloadUrl = solicitarUrlDescargaCurseForge(projectId, fileId);
				}

				if (downloadUrl == null || downloadUrl.trim().isEmpty()) {
					total.saltados++;
					total.mensajes.add("Archivo CurseForge sin URL de descarga: " + projectId + "/" + fileId);
					continue;
				}

				String carpetaTipo = obtenerCarpetaDestinoPorTipoCurseForge(modCF, archivoCF, nombreArchivo);
				String rutaRelativa = carpetaTipo + "/" + nombreArchivo;

				if (!rutaSegura(rutaRelativa)) {
					total.saltados++;
					total.mensajes.add("Ruta insegura calculada desde CurseForge: " + rutaRelativa);
					continue;
				}

				Path destino = carpetaDestino.resolve(rutaRelativa).normalize();

				if (!destino.toAbsolutePath().normalize().startsWith(carpetaDestino)) {
					total.saltados++;
					total.mensajes.add("Ruta intenta salir de la carpeta destino: " + rutaRelativa);
					continue;
				}

				byte[] bytes = descargarBytes(downloadUrl);

				String sha1Api = obtenerHashDesdeArchivoCF(dataArchivo, 1);
				if (sha1Api != null && !sha1Api.trim().isEmpty()) {
					verificarSha1SiExiste(bytes, sha1Api, rutaRelativa);
				}

				InfoEntradaImportacion info = crearInfoSimple(rutaRelativa);
				info.esMod = rutaRelativa.startsWith("mods/");
				info.curseForgeProjectId = String.valueOf(projectId);
				info.curseForgeFileId = String.valueOf(fileId);
				info.sha1 = sha1Api;
				info.fechaModificacion = parsearFecha(obtenerCadenaSeguro(dataArchivo.obtener("fileDate"), ""));
				info.tamanoDeclarado = obtenerLargoSeguro(dataArchivo.obtener("fileLength"), bytes.length);

				mezclar(total, instalarEntradaConConflictos(bytes, info, destino, politica));
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
				total.errores++;
				total.mensajes
						.add("No se pudo descargar CurseForge " + projectId + "/" + fileId + ": " + t.getMessage());
			}
		}

		return total;
	}

	private static Json.Nodo solicitarModCurseForge(long projectId) throws IOException {
		String endpoint = ProveedorModsCurseForge.ENDPOINT;
		if (!endpoint.endsWith("/")) {
			endpoint += "/";
		}

		String url = endpoint + "v1/mods/" + projectId;
		return getJson(url, ProveedorModsCurseForge.obtenerClaveApi());
	}

	private static Json.Nodo solicitarArchivoCurseForge(long projectId, long fileId) throws IOException {
		String endpoint = ProveedorModsCurseForge.ENDPOINT;
		if (!endpoint.endsWith("/")) {
			endpoint += "/";
		}

		String url = endpoint + "v1/mods/" + projectId + "/files/" + fileId;
		return getJson(url, ProveedorModsCurseForge.obtenerClaveApi());
	}

	private static String solicitarUrlDescargaCurseForge(long projectId, long fileId) throws IOException {
		String endpoint = ProveedorModsCurseForge.ENDPOINT;
		if (!endpoint.endsWith("/")) {
			endpoint += "/";
		}

		String url = endpoint + "v1/mods/" + projectId + "/files/" + fileId + "/download-url";
		Json.Nodo respuesta = getJson(url, ProveedorModsCurseForge.obtenerClaveApi());
		return obtenerCadenaSeguro(respuesta.obtener("data"), "");
	}

	private static String obtenerCarpetaDestinoPorTipoCurseForge(Json.Nodo modCF, Json.Nodo archivoCF,
			String nombreArchivo) {

		long classId = obtenerLargoSeguro(modCF.obtener("data").obtener("classId"), 0L);
		String slugClase = obtenerSlugClasePrincipal(modCF);

		// IDs comunes de CurseForge/Minecraft:
		// 6 = Mods
		// 12 = Resource Packs / Texture Packs
		// 17 = Worlds
		// 6552 = Shader Packs
		if (classId == 6L || "mc-mods".equals(slugClase) || "mods".equals(slugClase)) {
			return "mods";
		}

		if (classId == 12L || "texture-packs".equals(slugClase) || "resource-packs".equals(slugClase)) {
			return "resourcepacks";
		}

		if (classId == 6552L || "shaders".equals(slugClase) || "shader-packs".equals(slugClase)) {
			return "shaderpacks";
		}

		if (classId == 17L || "worlds".equals(slugClase)) {
			return "saves";
		}

		String nombre = nombreArchivo == null ? "" : nombreArchivo.toLowerCase();

		if (nombre.endsWith(".jar")) {
			return "mods";
		}

		if (nombre.contains("shader")) {
			return "shaderpacks";
		}

		if (nombre.endsWith(".zip")) {
			return "resourcepacks";
		}

		return "mods";
	}

	private static String obtenerSlugClasePrincipal(Json.Nodo modCF) {
		try {
			Json.Nodo categorias = modCF.obtener("data").obtener("categories");

			if (categorias == null || !categorias.esArreglo()) {
				return "";
			}

			for (int i = 0; i < categorias.tamano(); i++) {
				Json.Nodo categoria = categorias.en(i);
				boolean esClase = obtenerBooleanoSeguro(categoria.obtener("isClass"), false);

				if (esClase) {
					return obtenerCadenaSeguro(categoria.obtener("slug"), "").toLowerCase();
				}
			}
		} catch (Throwable t) {
			return "";
		}

		return "";
	}

	private static String obtenerHashDesdeArchivoCF(Json.Nodo dataArchivo, int algoritmo) {
		try {
			Json.Nodo hashes = dataArchivo.obtener("hashes");

			if (hashes == null || !hashes.esArreglo()) {
				return "";
			}

			for (int i = 0; i < hashes.tamano(); i++) {
				Json.Nodo h = hashes.en(i);
				int algo = obtenerEnteroSeguro(h.obtener("algo"), 0);

				if (algo == algoritmo) {
					return obtenerCadenaSeguro(h.obtener("value"), "");
				}
			}
		} catch (Throwable t) {
			return "";
		}

		return "";
	}

	private static Json.Nodo getJson(String url, String claveApi) throws IOException {
		HttpURLConnection con = null;

		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(20000);
			con.setReadTimeout(30000);
			con.setRequestProperty("Accept", "application/json");

			if (claveApi != null && !claveApi.trim().isEmpty()
					&& !"usar_klauncher_endpoint_si_no_tienes_clave_api".equals(claveApi)) {
				con.setRequestProperty("x-api-key", claveApi.trim());
			}

			int codigo = con.getResponseCode();
			InputStream is = codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream();
			String respuesta = new String(leerBytes(is), StandardCharsets.UTF_8);

			if (codigo < 200 || codigo >= 300) {
				throw new IOException("HTTP " + codigo + ": " + respuesta);
			}

			return Json.leer(respuesta);
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

	private static byte[] descargarBytes(String url) throws IOException {
		HttpURLConnection con = null;

		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(20000);
			con.setReadTimeout(60000);
			con.setRequestProperty("User-Agent", "CrashDetectorMC/1.0");

			int codigo = con.getResponseCode();
			if (codigo < 200 || codigo >= 300) {
				throw new IOException("No se pudo descargar " + url + " HTTP " + codigo);
			}

			return leerBytes(con.getInputStream());
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

	private static byte[] leerBytes(InputStream is) throws IOException {
		if (is == null) {
			return new byte[0];
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int leidos;

		while ((leidos = is.read(buffer)) != -1) {
			baos.write(buffer, 0, leidos);
		}

		return baos.toByteArray();
	}

	private static Map<String, EntradaZipLeida> leerEntradasZip(Path zip) throws IOException {
		Map<String, EntradaZipLeida> ret = new LinkedHashMap<String, EntradaZipLeida>();

		try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zip))) {
			ZipEntry e;

			while ((e = zis.getNextEntry()) != null) {
				if (e.isDirectory()) {
					continue;
				}

				String nombre = e.getName().replace('\\', '/');
				byte[] bytes = leerBytes(zis);

				EntradaZipLeida entrada = new EntradaZipLeida();
				entrada.nombre = nombre;
				entrada.bytes = bytes;
				entrada.fechaModificacion = e.getTime();

				ret.put(nombre, entrada);
			}
		}

		return ret;
	}

	private static Json.Nodo leerJsonDesdeZip(Map<String, EntradaZipLeida> entradas, String nombre) throws IOException {
		for (EntradaZipLeida e : entradas.values()) {
			if (nombre.equalsIgnoreCase(e.nombre)) {
				return Json.leer(new String(e.bytes, StandardCharsets.UTF_8));
			}
		}

		return null;
	}

	private static InfoEntradaImportacion crearInfoSimple(String rutaRelativa) {
		InfoEntradaImportacion info = new InfoEntradaImportacion();
		info.rutaRelativa = rutaRelativa.replace('\\', '/');
		info.nombreEntrada = rutaRelativa;
		info.esMod = info.rutaRelativa.startsWith("mods/");
		return info;
	}

	private static void actualizarTLauncherAdditionalDespuesDeImportar(Path carpetaDestino) {
		try {
			ActualizadorTLauncherAdditional.actualizar(carpetaDestino);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private static void verificarSha1SiExiste(byte[] bytes, String sha1Esperado, String ruta) throws IOException {
		if (sha1Esperado == null || sha1Esperado.trim().isEmpty()) {
			return;
		}

		String real = sha1(bytes);

		if (!real.equalsIgnoreCase(sha1Esperado.trim())) {
			throw new IOException("SHA-1 no coincide para " + ruta + ". Esperado: " + sha1Esperado + ", real: " + real);
		}
	}

	private static String sha1(byte[] bytes) throws IOException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(bytes);

			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			return sb.toString();
		} catch (Throwable t) {
			throw new IOException("No se pudo calcular SHA-1.", t);
		}
	}

	private static long parsearFecha(String fecha) {
		try {
			if (fecha == null || fecha.trim().isEmpty()) {
				return 0L;
			}

			return java.time.Instant.parse(fecha).toEpochMilli();
		} catch (Throwable t) {
			return 0L;
		}
	}

	private static void mezclar(ResultadoImportacion total, ResultadoImportacion r) {
		if (r == null) {
			return;
		}

		total.copiados += r.copiados;
		total.reemplazados += r.reemplazados;
		total.saltados += r.saltados;
		total.renombrados += r.renombrados;
		total.errores += r.errores;
		total.mensajes.addAll(r.mensajes);
	}

	private static boolean rutaSegura(String ruta) {
		if (ruta == null) {
			return false;
		}

		String r = ruta.replace('\\', '/');

		return !r.startsWith("/") && !r.contains("../") && !r.equals("..") && !r.contains(":/");
	}

	private static String obtenerCadenaSeguro(Json.Nodo nodo, String def) {
		try {
			String s = nodo.comoCadena();
			return s == null ? def : s;
		} catch (Throwable t) {
			return def;
		}
	}

	private static long obtenerLargoSeguro(Json.Nodo nodo, long def) {
		try {
			return nodo.comoLargo();
		} catch (Throwable t) {
			return def;
		}
	}

	private static int obtenerEnteroSeguro(Json.Nodo nodo, int def) {
		try {
			return nodo.comoEntero();
		} catch (Throwable t) {
			return def;
		}
	}

	private static boolean obtenerBooleanoSeguro(Json.Nodo nodo, boolean def) {
		try {
			return nodo.comoBooleano();
		} catch (Throwable t) {
			return def;
		}
	}

	private static class EntradaZipLeida {
		String nombre;
		byte[] bytes;
		long fechaModificacion;
	}
}