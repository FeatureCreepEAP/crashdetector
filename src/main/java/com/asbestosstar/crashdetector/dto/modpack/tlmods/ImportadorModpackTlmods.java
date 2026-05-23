package com.asbestosstar.crashdetector.dto.modpack.tlmods;

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
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.curseforge.ProveedorModsCurseForge;
import com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ImportadorModpack;
import com.asbestosstar.crashdetector.dto.modpack.importar.InfoEntradaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;

public class ImportadorModpackTlmods implements ImportadorModpack {

	private static final String NOMBRE_TLAUNCHER_ADDITIONAL = "TLauncherAdditional.json";

	public static final ConfigString URL_BASE_ARCHIVOS_TLMODS = ConfigString.de("tlmods.url_base_archivos",
			"https://tlmods.org/files");

	@Override
	public String obtenerNombreImportador() {
		return "TLMods";
	}

	@Override
	public String obtenerExtensionModpack() {
		return "zip";
	}

	@Override
	public boolean puedeImportar(Path archivoModpack) {
		return puedeImportarArchivo(archivoModpack);
	}

	@Override
	public ResultadoImportacion importarModpack(Path archivoModpack, Path carpetaDestino, PoliticaImportacion politica)
			throws IOException {

		return importar(archivoModpack, carpetaDestino, politica, null);
	}

	public static boolean puedeImportarArchivo(Path archivoModpack) {
		if (archivoModpack == null || archivoModpack.getFileName() == null) {
			return false;
		}

		return archivoModpack.getFileName().toString().toLowerCase().endsWith(".zip");
	}

	public ResultadoImportacion importar(Path archivoModpack, Path carpetaDestino, PoliticaImportacion politica,
			ResolutorConflictosImportacion resolutor) throws IOException {

		if (archivoModpack == null || !Files.isRegularFile(archivoModpack)) {
			throw new IOException("El modpack TLMods no existe: " + archivoModpack);
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
		String prefijoRaiz = detectarPrefijoRaiz(entradas);

		Json.Nodo tla = leerTLauncherAdditionalDesdeZip(entradas, prefijoRaiz);
		Map<String, InfoEntradaImportacion> infoPorRuta = crearIndiceInfoDesdeTLauncherAdditional(tla);

		for (EntradaZipLeida entrada : entradas.values()) {
			String rutaRelativa = quitarPrefijoRaiz(entrada.nombre, prefijoRaiz);

			if (rutaRelativa == null || rutaRelativa.trim().isEmpty()) {
				continue;
			}

			if (!rutaSegura(rutaRelativa)) {
				total.saltados++;
				total.mensajes.add("Ruta insegura omitida: " + entrada.nombre);
				continue;
			}

			InfoEntradaImportacion info = infoPorRuta.get(rutaRelativa);
			if (info == null) {
				info = new InfoEntradaImportacion();
			}

			info.rutaRelativa = rutaRelativa;
			info.nombreEntrada = entrada.nombre;
			info.fechaModificacion = entrada.fechaModificacion;
			info.tamanoDeclarado = entrada.bytes.length;
			info.esMod = esRutaMod(rutaRelativa);

			Path destino = carpetaDestino.resolve(rutaRelativa).normalize();

			if (!destino.toAbsolutePath().normalize().startsWith(carpetaDestino)) {
				total.saltados++;
				total.mensajes.add("Ruta intenta salir de la carpeta destino: " + entrada.nombre);
				continue;
			}

			ResultadoImportacion r = instalarEntradaConConflictos(entrada.bytes, info, destino, politica, resolutor);
			mezclar(total, r);
		}

		if (tla != null) {
			ResultadoImportacion r = descargarArchivosFaltantesDesdeTLauncherAdditional(tla, carpetaDestino, politica,
					resolutor);
			mezclar(total, r);
		}

		actualizarTLauncherAdditionalDespuesDeImportar(carpetaDestino);

		return total;
	}

	public ResultadoImportacion descargarArchivosFaltantesDesdeTLauncherAdditional(Json.Nodo tla, Path carpetaDestino,
			PoliticaImportacion politica, ResolutorConflictosImportacion resolutor) throws IOException {

		ResultadoImportacion total = new ResultadoImportacion();

		Json.Nodo version = tla.obtener("modpack").obtener("version");

		mezclar(total, descargarFaltantesDeArreglo(version.obtener("mods"), carpetaDestino, politica, resolutor));
		mezclar(total,
				descargarFaltantesDeArreglo(version.obtener("resourcePacks"), carpetaDestino, politica, resolutor));
		mezclar(total,
				descargarFaltantesDeArreglo(version.obtener("shaderPacks"), carpetaDestino, politica, resolutor));
		mezclar(total, descargarFaltantesDeArreglo(version.obtener("dataPacks"), carpetaDestino, politica, resolutor));

		return total;
	}

	public ResultadoImportacion descargarFaltantesDeArreglo(Json.Nodo arreglo, Path carpetaDestino,
			PoliticaImportacion politica, ResolutorConflictosImportacion resolutor) throws IOException {

		ResultadoImportacion total = new ResultadoImportacion();

		if (arreglo == null || !arreglo.esArreglo()) {
			return total;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);
			Json.Nodo version = entrada.obtener("version");
			Json.Nodo metadata = version.obtener("metadata");

			String path = obtenerCadenaSeguro(metadata.obtener("path"), "");
			String url = obtenerCadenaSeguro(metadata.obtener("url"), "");
			String sha1Esperado = obtenerCadenaSeguro(metadata.obtener("sha1"), "");

			if (path == null || path.trim().isEmpty()) {
				continue;
			}

			if (!rutaSegura(path)) {
				total.saltados++;
				total.mensajes.add("Ruta insegura omitida desde TLauncherAdditional: " + path);
				continue;
			}

			Path destino = carpetaDestino.resolve(path).normalize();

			if (!destino.toAbsolutePath().normalize().startsWith(carpetaDestino)) {
				total.saltados++;
				total.mensajes.add("Ruta intenta salir de la carpeta destino: " + path);
				continue;
			}

			if (Files.isRegularFile(destino)) {
				continue;
			}

			try {
				byte[] bytes = null;

				long modId = obtenerLargoSeguro(entrada.obtener("id"), 0L);
				long fileId = obtenerLargoSeguro(version.obtener("id"), 0L);

				// Prioridad 1: CurseForge si existen IDs válidos.
				if (modId > 0L && fileId > 0L) {
					try {
						bytes = descargarBytesDesdeCurseForge(modId, fileId);
					} catch (Throwable t) {
						CrashDetectorLogger.logException(t);
					}
				}

				// Prioridad 2: URL TLMods o URL completa ya guardada.
				if (bytes == null) {
					String urlFinal = normalizarUrlTlmods(url);

					if (urlFinal == null || urlFinal.trim().isEmpty()) {
						continue;
					}

					bytes = descargarBytes(urlFinal);
				}

				verificarSha1SiExiste(bytes, sha1Esperado, path);

				InfoEntradaImportacion info = crearInfoDesdeEntradaTLauncher(entrada, path);
				info.tamanoDeclarado = bytes.length;
				info.sha1 = sha1Esperado;

				ResultadoImportacion r = instalarEntradaConConflictos(bytes, info, destino, politica, resolutor);
				mezclar(total, r);
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
				total.errores++;
				total.mensajes.add("No se pudo descargar archivo TLMods: " + path + " / " + t.getMessage());
			}
		}

		return total;
	}

	private static byte[] descargarBytesDesdeCurseForge(long modId, long fileId) throws IOException {
		String endpoint = ProveedorModsCurseForge.ENDPOINT;

		if (!endpoint.endsWith("/")) {
			endpoint += "/";
		}

		String urlApi = endpoint + "v1/mods/" + modId + "/files/" + fileId;
		Json.Nodo respuesta = getJson(urlApi, ProveedorModsCurseForge.obtenerClaveApi());

		String downloadUrl = obtenerCadenaSeguro(respuesta.obtener("data").obtener("downloadUrl"), "");

		if (downloadUrl == null || downloadUrl.trim().isEmpty()) {
			return null;
		}

		return descargarBytes(downloadUrl);
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
			con.setRequestProperty("User-Agent", "CrashDetectorMC/1.0");

			if (claveApi != null && !claveApi.trim().isEmpty()
					&& !"usar_klauncher_endpoint_si_no_tienes_clave_api".equals(claveApi)) {
				con.setRequestProperty("x-api-key", claveApi.trim());
			}

			int codigo = con.getResponseCode();
			String respuesta = new String(
					leerBytes(codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream()),
					StandardCharsets.UTF_8);

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

	private static String normalizarUrlTlmods(String url) {
		if (url == null || url.trim().isEmpty()) {
			return "";
		}

		String u = url.trim();

		if (u.startsWith("http://") || u.startsWith("https://")) {
			return u;
		}

		String base = URL_BASE_ARCHIVOS_TLMODS.obtener();

		if (base == null || base.trim().isEmpty()) {
			base = "https://tlmods.org/files";
		}

		while (base.endsWith("/")) {
			base = base.substring(0, base.length() - 1);
		}

		if (!u.startsWith("/")) {
			u = "/" + u;
		}

		return base + u;
	}

	private static void verificarSha1SiExiste(byte[] bytes, String sha1Esperado, String nombre) throws IOException {
		if (sha1Esperado == null || sha1Esperado.trim().isEmpty()) {
			return;
		}

		String real = sha1(bytes);

		if (!sha1Esperado.trim().equalsIgnoreCase(real)) {
			throw new IOException(
					"SHA-1 no coincide para " + nombre + ". Esperado: " + sha1Esperado + ", real: " + real);
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

	private static void actualizarTLauncherAdditionalDespuesDeImportar(Path carpetaDestino) {
		try {
			ActualizadorTLauncherAdditional.actualizar(carpetaDestino);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private static Map<String, InfoEntradaImportacion> crearIndiceInfoDesdeTLauncherAdditional(Json.Nodo tla) {
		Map<String, InfoEntradaImportacion> ret = new LinkedHashMap<String, InfoEntradaImportacion>();

		if (tla == null) {
			return ret;
		}

		try {
			Json.Nodo version = tla.obtener("modpack").obtener("version");

			agregarInfoDeArreglo(ret, version.obtener("mods"));
			agregarInfoDeArreglo(ret, version.obtener("resourcePacks"));
			agregarInfoDeArreglo(ret, version.obtener("shaderPacks"));
			agregarInfoDeArreglo(ret, version.obtener("dataPacks"));
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		return ret;
	}

	private static void agregarInfoDeArreglo(Map<String, InfoEntradaImportacion> ret, Json.Nodo arreglo) {
		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);
			String path = obtenerCadenaSeguro(entrada.obtener("version").obtener("metadata").obtener("path"), "");

			if (path == null || path.trim().isEmpty()) {
				continue;
			}

			ret.put(path.replace('\\', '/'), crearInfoDesdeEntradaTLauncher(entrada, path));
		}
	}

	private static InfoEntradaImportacion crearInfoDesdeEntradaTLauncher(Json.Nodo entrada, String path) {
		InfoEntradaImportacion info = new InfoEntradaImportacion();

		info.rutaRelativa = path.replace('\\', '/');
		info.nombreEntrada = path;
		info.esMod = esRutaMod(path);

		info.curseForgeProjectId = obtenerCadenaSeguro(entrada.obtener("id"), "");
		info.curseForgeFileId = obtenerCadenaSeguro(entrada.obtener("version").obtener("id"), "");

		info.modrinthProjectId = obtenerCadenaSeguro(entrada.obtener("modrinthProjectId"), "");
		info.modrinthVersionId = obtenerCadenaSeguro(entrada.obtener("version").obtener("modrinthVersionId"), "");

		info.sha1 = obtenerCadenaSeguro(entrada.obtener("version").obtener("metadata").obtener("sha1"), "");

		return info;
	}

	private static Json.Nodo leerTLauncherAdditionalDesdeZip(Map<String, EntradaZipLeida> entradas,
			String prefijoRaiz) {
		try {
			for (EntradaZipLeida entrada : entradas.values()) {
				String ruta = quitarPrefijoRaiz(entrada.nombre, prefijoRaiz);

				if (NOMBRE_TLAUNCHER_ADDITIONAL.equalsIgnoreCase(ruta)) {
					return Json.leer(new String(entrada.bytes, StandardCharsets.UTF_8));
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		return null;
	}

	private static Map<String, EntradaZipLeida> leerEntradasZip(Path archivoModpack) throws IOException {
		Map<String, EntradaZipLeida> ret = new LinkedHashMap<String, EntradaZipLeida>();

		try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(archivoModpack))) {
			ZipEntry ze;

			while ((ze = zis.getNextEntry()) != null) {
				if (ze.isDirectory()) {
					zis.closeEntry();
					continue;
				}

				String nombre = ze.getName().replace('\\', '/');

				if (!rutaSegura(nombre)) {
					zis.closeEntry();
					continue;
				}

				EntradaZipLeida entrada = new EntradaZipLeida();
				entrada.nombre = nombre;
				entrada.fechaModificacion = ze.getTime();
				entrada.bytes = leerBytes(zis);

				ret.put(nombre, entrada);
				zis.closeEntry();
			}
		}

		return ret;
	}

	private static String detectarPrefijoRaiz(Map<String, EntradaZipLeida> entradas) {
		String prefijo = null;

		for (String ruta : entradas.keySet()) {
			String r = ruta.replace('\\', '/');
			int idx = r.indexOf('/');

			if (idx <= 0) {
				return "";
			}

			String primer = r.substring(0, idx + 1);

			if (prefijo == null) {
				prefijo = primer;
			} else if (!prefijo.equals(primer)) {
				return "";
			}
		}

		return prefijo == null ? "" : prefijo;
	}

	private static String quitarPrefijoRaiz(String ruta, String prefijoRaiz) {
		if (ruta == null) {
			return null;
		}

		String r = ruta.replace('\\', '/');

		if (prefijoRaiz != null && !prefijoRaiz.isEmpty() && r.startsWith(prefijoRaiz)) {
			return r.substring(prefijoRaiz.length());
		}

		return r;
	}

	public ResultadoImportacion instalarEntradaConConflictos(byte[] bytesEntrada, InfoEntradaImportacion info,
			Path destino, PoliticaImportacion politica, ResolutorConflictosImportacion resolutor) throws IOException {

		ResultadoImportacion resultado = new ResultadoImportacion();

		if (bytesEntrada == null) {
			resultado.errores++;
			resultado.mensajes.add("Entrada sin bytes: " + destino);
			return resultado;
		}

		if (destino == null) {
			resultado.errores++;
			resultado.mensajes.add("Destino nulo.");
			return resultado;
		}

		if (destino.getParent() != null) {
			Files.createDirectories(destino.getParent());
		}

		if (!Files.exists(destino)) {
			Files.write(destino, bytesEntrada, java.nio.file.StandardOpenOption.CREATE,
					java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
			resultado.copiados++;
			return resultado;
		}

		ConflictoImportacion conflicto = crearConflicto(bytesEntrada, info, destino);

		ConflictoImportacion.Decision decision;

		if (politica != null && politica.reemplazarTodo) {
			decision = ConflictoImportacion.Decision.REEMPLAZAR;
		} else if (politica != null && politica.saltarTodo) {
			decision = ConflictoImportacion.Decision.SALTAR;
		} else if (politica != null && politica.renombrarTodo) {
			decision = ConflictoImportacion.Decision.RENOMBRAR;
		} else if (politica != null && politica.noPreguntarSiImportadoEsMasNuevo && conflicto.importadoPareceMasNuevo) {
			decision = ConflictoImportacion.Decision.REEMPLAZAR;
		} else if (resolutor != null) {
			decision = resolutor.resolverConflicto(conflicto, politica);
		} else {
			decision = ConflictoImportacion.Decision.SALTAR;
		}

		if (decision == ConflictoImportacion.Decision.REEMPLAZAR) {
			Files.write(destino, bytesEntrada, java.nio.file.StandardOpenOption.CREATE,
					java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
			resultado.reemplazados++;
			return resultado;
		}

		if (decision == ConflictoImportacion.Decision.RENOMBRAR) {
			Path renombrado = crearRutaRenombrada(destino);
			Files.write(renombrado, bytesEntrada, java.nio.file.StandardOpenOption.CREATE_NEW);
			resultado.renombrados++;
			return resultado;
		}

		resultado.saltados++;
		return resultado;
	}

	private static ConflictoImportacion crearConflicto(byte[] bytesEntrada, InfoEntradaImportacion info, Path destino)
			throws IOException {

		ConflictoImportacion conflicto = new ConflictoImportacion();

		conflicto.tipo = info != null && info.esMod ? ConflictoImportacion.Tipo.MOD : ConflictoImportacion.Tipo.ARCHIVO;

		conflicto.archivoExistente = destino;
		conflicto.archivoNuevo = null;
		conflicto.rutaRelativa = info != null ? info.rutaRelativa : destino.getFileName().toString();

		conflicto.tamanoExistente = Files.size(destino);
		conflicto.tamanoNuevo = bytesEntrada.length;

		conflicto.fechaExistente = Files.getLastModifiedTime(destino).toMillis();
		conflicto.fechaNueva = info != null ? info.fechaModificacion : 0L;

		if (info != null) {
			conflicto.curseForgeIdNuevo = info.curseForgeProjectId;
			conflicto.modrinthProjectIdNuevo = info.modrinthProjectId;
			conflicto.modrinthVersionIdNuevo = info.modrinthVersionId;
		}

		conflicto.importadoPareceMasNuevo = conflicto.fechaNueva > 0 && conflicto.fechaNueva > conflicto.fechaExistente;
		conflicto.importadoPareceMasViejo = conflicto.fechaNueva > 0 && conflicto.fechaNueva < conflicto.fechaExistente;

		return conflicto;
	}

	public Path crearRutaRenombrada(Path destino) {
		String nombre = destino.getFileName().toString();
		String base = nombre;
		String ext = "";

		int punto = nombre.lastIndexOf('.');
		if (punto > 0) {
			base = nombre.substring(0, punto);
			ext = nombre.substring(punto);
		}

		Path carpeta = destino.getParent();

		for (int i = 1; i < 10000; i++) {
			Path candidato = carpeta.resolve(base + "_importado_" + i + ext);
			if (!Files.exists(candidato)) {
				return candidato;
			}
		}

		return carpeta.resolve(base + "_importado_" + System.currentTimeMillis() + ext);
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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int leidos;

		while ((leidos = is.read(buffer)) != -1) {
			baos.write(buffer, 0, leidos);
		}

		return baos.toByteArray();
	}

	private static boolean rutaSegura(String ruta) {
		if (ruta == null || ruta.trim().isEmpty()) {
			return false;
		}

		String r = ruta.replace('\\', '/');

		return !r.startsWith("/") && !r.contains("../") && !r.startsWith("..");
	}

	private static boolean esRutaMod(String rutaRelativa) {
		if (rutaRelativa == null) {
			return false;
		}

		String r = rutaRelativa.replace('\\', '/').toLowerCase();
		return r.startsWith("mods/") && r.endsWith(".jar");
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

	private static String obtenerCadenaSeguro(Json.Nodo nodo, String def) {
		try {
			String v = nodo.comoCadena();
			return v != null ? v : def;
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

	private static class EntradaZipLeida {
		public String nombre;
		public byte[] bytes;
		public long fechaModificacion;
	}
}