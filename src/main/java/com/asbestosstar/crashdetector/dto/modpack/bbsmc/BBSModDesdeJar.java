package com.asbestosstar.crashdetector.dto.modpack.bbsmc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

import com.asbestosstar.crashdetector.config.json.Json;

public class BBSModDesdeJar {

	public static final String ENDPOINT_BBSMC = "https://api.bbsmc.net/v2";

	public enum TipoArchivoBBS {
		MOD, RESOURCE_PACK, SHADER_PACK, DATA_PACK
	}

	public static class ArchivoLocalBBS {
		public Path archivo;
		public Path rutaRelativa;
		public TipoArchivoBBS tipo;
		public String sha1;
		public long tamano;

		public ArchivoLocalBBS(Path archivo, Path rutaRelativa, TipoArchivoBBS tipo) {
			this.archivo = archivo;
			this.rutaRelativa = rutaRelativa;
			this.tipo = tipo;
		}
	}

	public static class ResultadoBBS {
		public List<Json.Nodo> mods = new ArrayList<Json.Nodo>();
		public List<Json.Nodo> resourcePacks = new ArrayList<Json.Nodo>();
		public List<Json.Nodo> shaderPacks = new ArrayList<Json.Nodo>();
		public List<Json.Nodo> dataPacks = new ArrayList<Json.Nodo>();
		public List<ArchivoLocalBBS> sinCoincidencia = new ArrayList<ArchivoLocalBBS>();
	}

	public static ResultadoBBS analizarInstancia(Path carpetaInstancia) throws IOException {
		return analizarInstanciaConEndpoint(carpetaInstancia, ENDPOINT_BBSMC);
	}

	public static ResultadoBBS analizarArchivos(Path carpetaInstancia, List<ArchivoLocalBBS> archivos)
			throws IOException {
		return analizarArchivosConEndpoint(carpetaInstancia, archivos, ENDPOINT_BBSMC);
	}

	protected static ResultadoBBS analizarInstanciaConEndpoint(Path carpetaInstancia, String endpoint)
			throws IOException {
		List<ArchivoLocalBBS> archivos = obtenerArchivosAnalizables(carpetaInstancia);
		return analizarArchivosConEndpoint(carpetaInstancia, archivos, endpoint);
	}

	protected static ResultadoBBS analizarArchivosConEndpoint(Path carpetaInstancia, List<ArchivoLocalBBS> archivos,
			String endpoint) throws IOException {

		ResultadoBBS resultado = new ResultadoBBS();

		if (archivos == null || archivos.isEmpty()) {
			return resultado;
		}

		for (ArchivoLocalBBS archivo : archivos) {
			archivo.sha1 = calcularSha1(archivo.archivo);
			archivo.tamano = Files.size(archivo.archivo);

			Json.Nodo version = null;
			Json.Nodo proyecto = null;

			try {
				version = solicitarVersionPorSha1(endpoint, archivo.sha1);
				String projectId = obtenerCadenaSeguro(version.obtener("project_id"), "");
				if (projectId != null && !projectId.isEmpty()) {
					proyecto = solicitarProyecto(endpoint, projectId);
				}
			} catch (IOException ex) {
				resultado.sinCoincidencia.add(archivo);
				continue;
			}

			Json.Nodo entrada = crearEntradaTLauncherDesdeCoincidencia(archivo, version, proyecto);

			if (esEndpointModrinth(endpoint)) {
				agregarIdsModrinthOpcionales(entrada, version);
			}

			if (archivo.tipo == TipoArchivoBBS.MOD) {
				resultado.mods.add(entrada);
			} else if (archivo.tipo == TipoArchivoBBS.RESOURCE_PACK) {
				resultado.resourcePacks.add(entrada);
			} else if (archivo.tipo == TipoArchivoBBS.SHADER_PACK) {
				resultado.shaderPacks.add(entrada);
			} else if (archivo.tipo == TipoArchivoBBS.DATA_PACK) {
				resultado.dataPacks.add(entrada);
			}
		}

		return resultado;
	}

	public static List<ArchivoLocalBBS> obtenerArchivosAnalizables(Path carpetaInstancia) throws IOException {
		List<ArchivoLocalBBS> ret = new ArrayList<ArchivoLocalBBS>();

		agregarArchivosDeCarpeta(ret, carpetaInstancia, "mods", TipoArchivoBBS.MOD, ".jar");
		agregarArchivosDeCarpeta(ret, carpetaInstancia, "resourcepacks", TipoArchivoBBS.RESOURCE_PACK, ".zip");
		agregarArchivosDeCarpeta(ret, carpetaInstancia, "shaderpacks", TipoArchivoBBS.SHADER_PACK, ".zip");
		agregarArchivosDeCarpeta(ret, carpetaInstancia, "datapacks", TipoArchivoBBS.DATA_PACK, ".zip");

		return ret;
	}

	private static void agregarArchivosDeCarpeta(List<ArchivoLocalBBS> lista, Path carpetaInstancia,
			String nombreCarpeta, TipoArchivoBBS tipo, String extension) throws IOException {

		Path carpeta = carpetaInstancia.resolve(nombreCarpeta);

		if (!Files.isDirectory(carpeta)) {
			return;
		}

		try (java.util.stream.Stream<Path> stream = Files.list(carpeta)) {
			stream.filter(Files::isRegularFile).forEach(p -> {
				String nombre = p.getFileName().toString().toLowerCase();

				if (!nombre.endsWith(extension)) {
					return;
				}

				Path relativa = carpetaInstancia.toAbsolutePath().normalize()
						.relativize(p.toAbsolutePath().normalize());

				lista.add(new ArchivoLocalBBS(p.toAbsolutePath().normalize(), relativa, tipo));
			});
		}
	}

	protected static Json.Nodo solicitarVersionPorSha1(String endpoint, String sha1) throws IOException {
		String url = normalizarEndpoint(endpoint) + "/version_file/" + URLEncoder.encode(sha1, "UTF-8")
				+ "?algorithm=sha1";
		return getJson(url);
	}

	protected static Json.Nodo solicitarProyecto(String endpoint, String projectId) throws IOException {
		String url = normalizarEndpoint(endpoint) + "/project/" + URLEncoder.encode(projectId, "UTF-8");
		return getJson(url);
	}

	protected static Json.Nodo getJson(String url) throws IOException {
		HttpURLConnection con = null;

		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(20000);
			con.setReadTimeout(30000);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("User-Agent", "CrashDetectorMC/1.0");

			int codigo = con.getResponseCode();
			InputStream is = codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream();
			String respuesta = leerCompleto(is);

			if (codigo < 200 || codigo >= 300) {
				throw new IOException("Error BBSMC/Modrinth HTTP " + codigo + ": " + respuesta);
			}

			return Json.leer(respuesta);
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

	public static Json.Nodo crearEntradaTLauncherDesdeCoincidencia(ArchivoLocalBBS archivo, Json.Nodo version,
			Json.Nodo proyecto) {

		Json.Nodo entrada = Json.crearObjeto();

		String projectIdTexto = obtenerCadenaSeguro(version.obtener("project_id"), "");
		String versionIdTexto = obtenerCadenaSeguro(version.obtener("id"), "");

		long projectId = idTextoALargoEstable(projectIdTexto);
		long versionId = idTextoALargoEstable(versionIdTexto);

		String slug = obtenerCadenaSeguro(proyecto.obtener("slug"),
				limpiarSlug(archivo.archivo.getFileName().toString()));
		String tituloProyecto = obtenerCadenaPrimera(proyecto.obtener("title"), proyecto.obtener("name"),
				archivo.archivo.getFileName().toString());

		String nombreVersion = obtenerCadenaPrimera(version.obtener("name"), version.obtener("version_number"),
				archivo.archivo.getFileName().toString());

		entrada.obtener("stateGameElement").poner("active");
		entrada.obtener("id").poner(projectId);
		entrada.obtener("name").poner(tituloProyecto);
		entrada.obtener("author").poner("");
		entrada.obtener("linkProject").poner(crearLinkProyecto(projectIdTexto, slug));
		entrada.obtener("favorite").poner(false);

		// Campos opcionales para exportadores que necesitan IDs de Modrinth.
		// No afectan la importación TLMods.
		entrada.obtener("modrinthProjectId").poner("");
		entrada.obtener("modrinthVersionId").poner("");

		entrada.obtener("categories").poner(crearCategorias(proyecto.obtener("categories")));
		entrada.obtener("picture").poner(0L);
		entrada.obtener("pictures").poner(Json.crearObjeto().obtener("arr"));
		entrada.obtener("lanName").poner(slug);

		Json.Nodo versionOut = entrada.obtener("version");
		versionOut.obtener("id").poner(versionId);
		versionOut.obtener("name").poner(nombreVersion);
		versionOut.obtener("updateDate")
				.poner(parsearFecha(obtenerCadenaSeguro(version.obtener("date_published"), null)));
		versionOut.obtener("type").poner(obtenerCadenaSeguro(version.obtener("version_type"), "release"));
		versionOut.obtener("gameVersionsDTO").poner(crearVersionesJuego(version.obtener("game_versions")));

		Json.Nodo archivoPrimario = obtenerArchivoPrimario(version);

		Json.Nodo metadata = versionOut.obtener("metadata");
		metadata.obtener("sha1").poner(obtenerSha1DesdeArchivo(archivoPrimario, archivo.sha1));
		metadata.obtener("size").poner(obtenerLargoSeguro(archivoPrimario.obtener("size"), archivo.tamano));
		metadata.obtener("path").poner(archivo.rutaRelativa.toString().replace('\\', '/'));
		metadata.obtener("url").poner(
				obtenerCadenaSeguro(archivoPrimario.obtener("url"), crearUrlMetadata(archivo, projectId, versionId)));

		versionOut.obtener("installed").poner(0L);
		versionOut.obtener("available").poner(false);
		versionOut.obtener("remove").poner(false);
		versionOut.obtener("minecraftVersionTypes").poner(crearTiposMinecraft(version.obtener("loaders")));

		entrada.obtener("popularity").poner(5);
		entrada.obtener("downloadMonth").poner(0L);
		entrada.obtener("userInstall").poner(false);
		entrada.obtener("populateStatus").poner(false);
		entrada.obtener("dependencies").poner(crearDependencias(version.obtener("dependencies")));
		entrada.obtener("lastGameVersion").poner(crearUltimaVersionJuego(version.obtener("game_versions")));
		entrada.obtener("updated").poner(parsearFecha(obtenerCadenaSeguro(proyecto.obtener("updated"), null)));
		entrada.obtener("update").poner(parsearFecha(obtenerCadenaSeguro(proyecto.obtener("updated"), null)));
		entrada.obtener("downloadALL").poner(obtenerLargoSeguro(proyecto.obtener("downloads"), 0L));
		entrada.obtener("available").poner(true);
		entrada.obtener("parser").poner(true);
		entrada.obtener("totalComments").poner(0);
		entrada.obtener("adult").poner(false);

		return entrada;
	}

	protected static void agregarIdsModrinthOpcionales(Json.Nodo entrada, Json.Nodo version) {
		String projectIdTexto = obtenerCadenaSeguro(version.obtener("project_id"), "");
		String versionIdTexto = obtenerCadenaSeguro(version.obtener("id"), "");

		// Campos opcionales. TLMods los puede ignorar.
		// Sirven para exportar mrpack, packwiz u otros formatos que requieren IDs MR.
		entrada.obtener("modrinthProjectId").poner(projectIdTexto);
		entrada.obtener("modrinthVersionId").poner(versionIdTexto);
	}

	protected static boolean esEndpointModrinth(String endpoint) {
		return endpoint != null && endpoint.toLowerCase().contains("modrinth.com");
	}

	private static Json.Nodo obtenerArchivoPrimario(Json.Nodo version) {
		Json.Nodo archivos = version.obtener("files");

		if (archivos != null && archivos.esArreglo() && archivos.tamano() > 0) {
			for (int i = 0; i < archivos.tamano(); i++) {
				Json.Nodo f = archivos.en(i);
				if (obtenerBooleanoSeguro(f.obtener("primary"), false)) {
					return f;
				}
			}

			return archivos.en(0);
		}

		return Json.crearObjeto();
	}

	private static String obtenerSha1DesdeArchivo(Json.Nodo archivo, String sha1Local) {
		String sha1 = obtenerCadenaSeguro(archivo.obtener("hashes").obtener("sha1"), null);

		if (sha1 != null && !sha1.isEmpty()) {
			return sha1;
		}

		return sha1Local;
	}

	private static String crearLinkProyecto(String projectId, String slug) {
		if (slug != null && !slug.isEmpty()) {
			return "https://bbsmc.net/mod/" + slug;
		}

		if (projectId != null && !projectId.isEmpty()) {
			return "https://bbsmc.net/mod/" + projectId;
		}

		return "https://bbsmc.net/";
	}

	private static String crearUrlMetadata(ArchivoLocalBBS archivo, long modId, long fileId) {
		String nombre = archivo.archivo.getFileName().toString();

		if (archivo.tipo == TipoArchivoBBS.RESOURCE_PACK) {
			return "/resourcepacks/" + modId + "/" + fileId + "/" + nombre;
		}

		if (archivo.tipo == TipoArchivoBBS.SHADER_PACK) {
			return "/shaderpacks/" + modId + "/" + fileId + "/" + nombre;
		}

		if (archivo.tipo == TipoArchivoBBS.DATA_PACK) {
			return "/datapacks/" + modId + "/" + fileId + "/" + nombre;
		}

		return "/mods/" + modId + "/" + fileId + "/" + nombre;
	}

	private static Json.Nodo crearCategorias(Json.Nodo categorias) {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		if (categorias != null && categorias.esArreglo()) {
			for (int i = 0; i < categorias.tamano(); i++) {
				String nombre = categorias.en(i).comoCadena();

				if (nombre == null || nombre.trim().isEmpty()) {
					continue;
				}

				Json.Nodo out = Json.crearObjeto();
				out.obtener("id").poner(idTextoALargoEstable(nombre));
				out.obtener("name").poner(nombre);
				out.obtener("shortName").poner(nombre);
				out.obtener("nesting").poner(0);

				arr.agregar(out);
			}
		}

		return arr;
	}

	private static Json.Nodo crearVersionesJuego(Json.Nodo versiones) {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		if (versiones != null && versiones.esArreglo()) {
			for (int i = 0; i < versiones.tamano(); i++) {
				String nombre = versiones.en(i).comoCadena();

				if (nombre == null || nombre.trim().isEmpty()) {
					continue;
				}

				if (!pareceVersionMinecraft(nombre)) {
					continue;
				}

				Json.Nodo v = Json.crearObjeto();
				v.obtener("id").poner(idVersionMinecraftAproximado(nombre));
				v.obtener("name").poner(nombre);
				arr.agregar(v);
			}
		}

		return arr;
	}

	private static Json.Nodo crearTiposMinecraft(Json.Nodo loaders) {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		if (loaders == null || !loaders.esArreglo()) {
			return arr;
		}

		for (int i = 0; i < loaders.tamano(); i++) {
			String loader = loaders.en(i).comoCadena();

			if (loader == null) {
				continue;
			}

			String l = loader.toLowerCase();

			if ("forge".equals(l)) {
				arr.agregar(crearTipoMinecraft(1, "forge"));
			} else if ("fabric".equals(l)) {
				arr.agregar(crearTipoMinecraft(2, "fabric"));
			} else if ("quilt".equals(l)) {
				arr.agregar(crearTipoMinecraft(4, "quilt"));
			} else if ("neoforge".equals(l)) {
				arr.agregar(crearTipoMinecraft(5, "neoForge"));
			} else if ("liteloader".equals(l)) {
				arr.agregar(crearTipoMinecraft(6, "liteloader"));
			} else if ("rift".equals(l)) {
				arr.agregar(crearTipoMinecraft(7, "rift"));
			}
		}

		return arr;
	}

	private static Json.Nodo crearTipoMinecraft(long id, String nombre) {
		Json.Nodo obj = Json.crearObjeto();
		obj.obtener("id").poner(id);
		obj.obtener("name").poner(nombre);
		return obj;
	}

	private static Json.Nodo crearDependencias(Json.Nodo dependencias) {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		if (dependencias != null && dependencias.esArreglo()) {
			for (int i = 0; i < dependencias.tamano(); i++) {
				Json.Nodo dep = dependencias.en(i);

				Json.Nodo out = Json.crearObjeto();
				out.obtener("gameType").poner("mod");
				out.obtener("id").poner(idTextoALargoEstable(obtenerCadenaSeguro(dep.obtener("version_id"), "")));
				out.obtener("name").poner("");
				out.obtener("gameEntityId")
						.poner(idTextoALargoEstable(obtenerCadenaSeguro(dep.obtener("project_id"), "")));
				out.obtener("dependencyType").poner(obtenerCadenaSeguro(dep.obtener("dependency_type"), "optional"));
				out.obtener("available").poner(false);

				arr.agregar(out);
			}
		}

		return arr;
	}

	private static Json.Nodo crearUltimaVersionJuego(Json.Nodo versiones) {
		Json.Nodo obj = Json.crearObjeto();

		if (versiones != null && versiones.esArreglo()) {
			for (int i = versiones.tamano() - 1; i >= 0; i--) {
				String nombre = versiones.en(i).comoCadena();

				if (pareceVersionMinecraft(nombre)) {
					obj.obtener("id").poner(idVersionMinecraftAproximado(nombre));
					obj.obtener("name").poner(nombre);
					return obj;
				}
			}
		}

		obj.obtener("id").poner(0);
		obj.obtener("name").poner("");
		return obj;
	}

	public static String calcularSha1(Path archivo) throws IOException {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");

			try (InputStream is = Files.newInputStream(archivo)) {
				byte[] buffer = new byte[8192];
				int leidos;

				while ((leidos = is.read(buffer)) != -1) {
					digest.update(buffer, 0, leidos);
				}
			}

			byte[] hash = digest.digest();
			StringBuilder sb = new StringBuilder();

			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}

			return sb.toString();
		} catch (Exception e) {
			throw new IOException("No se pudo calcular SHA-1 de " + archivo, e);
		}
	}

	private static String leerCompleto(InputStream is) throws IOException {
		if (is == null) {
			return "";
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int leidos;

		while ((leidos = is.read(buffer)) != -1) {
			baos.write(buffer, 0, leidos);
		}

		return new String(baos.toByteArray(), "UTF-8");
	}

	protected static String normalizarEndpoint(String endpoint) {
		if (endpoint.endsWith("/")) {
			return endpoint.substring(0, endpoint.length() - 1);
		}

		return endpoint;
	}

	protected static long idTextoALargoEstable(String texto) {
		if (texto == null || texto.isEmpty()) {
			return 0L;
		}

		CRC32 crc = new CRC32();
		try {
			crc.update(texto.getBytes("UTF-8"));
		} catch (Exception e) {
			crc.update(texto.getBytes());
		}

		return crc.getValue();
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

	private static boolean pareceVersionMinecraft(String texto) {
		return texto != null && texto.matches("\\d+\\.\\d+(\\.\\d+)?");
	}

	private static long idVersionMinecraftAproximado(String nombre) {
		if ("1.21.1".equals(nombre)) {
			return 61;
		}

		if ("1.20.1".equals(nombre)) {
			return 46;
		}

		if ("1.20".equals(nombre)) {
			return 45;
		}

		if ("1.19.4".equals(nombre)) {
			return 44;
		}

		if ("1.19.3".equals(nombre)) {
			return 43;
		}

		if ("1.19.2".equals(nombre)) {
			return 38;
		}

		return 0;
	}

	private static String limpiarSlug(String nombre) {
		if (nombre == null) {
			return "";
		}

		return nombre.replace(".jar", "").replace(".zip", "").toLowerCase().replaceAll("[^a-z0-9]+", "-")
				.replaceAll("^-+", "").replaceAll("-+$", "");
	}

	private static String obtenerCadenaPrimera(Json.Nodo a, Json.Nodo b, String def) {
		String av = obtenerCadenaSeguro(a, null);

		if (av != null && !av.isEmpty()) {
			return av;
		}

		String bv = obtenerCadenaSeguro(b, null);

		if (bv != null && !bv.isEmpty()) {
			return bv;
		}

		return def;
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

	private static boolean obtenerBooleanoSeguro(Json.Nodo nodo, boolean def) {
		try {
			return nodo.comoBooleano();
		} catch (Throwable t) {
			return def;
		}
	}
}