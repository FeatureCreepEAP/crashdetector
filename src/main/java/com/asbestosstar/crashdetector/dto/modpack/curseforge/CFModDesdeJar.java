package com.asbestosstar.crashdetector.dto.modpack.curseforge;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.config.json.Json;

public class CFModDesdeJar {

	public static final int GAME_ID_MINECRAFT = 432;

	public enum TipoArchivoCF {
		MOD, RESOURCE_PACK, SHADER_PACK, DATA_PACK
	}

	public static class ArchivoLocalCF {
		public Path archivo;
		public Path rutaRelativa;
		public TipoArchivoCF tipo;
		public long fingerprint;
		public String sha1;
		public long tamano;

		public ArchivoLocalCF(Path archivo, Path rutaRelativa, TipoArchivoCF tipo) {
			this.archivo = archivo;
			this.rutaRelativa = rutaRelativa;
			this.tipo = tipo;
		}
	}

	public static class ResultadoCF {
		public List<Json.Nodo> mods = new ArrayList<Json.Nodo>();
		public List<Json.Nodo> resourcePacks = new ArrayList<Json.Nodo>();
		public List<Json.Nodo> shaderPacks = new ArrayList<Json.Nodo>();
		public List<Json.Nodo> dataPacks = new ArrayList<Json.Nodo>();
		public List<ArchivoLocalCF> sinCoincidencia = new ArrayList<ArchivoLocalCF>();
	}

	public static ResultadoCF analizarInstancia(Path carpetaInstancia) throws IOException {
		List<ArchivoLocalCF> archivos = obtenerArchivosAnalizables(carpetaInstancia);
		return analizarArchivos(carpetaInstancia, archivos);
	}

	public static ResultadoCF analizarArchivos(Path carpetaInstancia, List<ArchivoLocalCF> archivos)
			throws IOException {
		ResultadoCF resultado = new ResultadoCF();

		if (archivos == null || archivos.isEmpty()) {
			return resultado;
		}

		for (ArchivoLocalCF archivo : archivos) {
			archivo.fingerprint = calcularFingerprintCurseForge(archivo.archivo);
			archivo.sha1 = calcularSha1(archivo.archivo);
			archivo.tamano = Files.size(archivo.archivo);
		}

		Json.Nodo respuesta = solicitarCoincidenciasPorFingerprints(archivos);
		Map<Long, Json.Nodo> coincidencias = indexarCoincidenciasPorFingerprint(respuesta);

		for (ArchivoLocalCF archivo : archivos) {
			Json.Nodo coincidencia = coincidencias.get(Long.valueOf(archivo.fingerprint));

			if (coincidencia == null) {
				resultado.sinCoincidencia.add(archivo);
				continue;
			}

			Json.Nodo entrada = crearEntradaTLauncherDesdeCoincidencia(archivo, coincidencia);

			if (archivo.tipo == TipoArchivoCF.MOD) {
				resultado.mods.add(entrada);
			} else if (archivo.tipo == TipoArchivoCF.RESOURCE_PACK) {
				resultado.resourcePacks.add(entrada);
			} else if (archivo.tipo == TipoArchivoCF.SHADER_PACK) {
				resultado.shaderPacks.add(entrada);
			} else if (archivo.tipo == TipoArchivoCF.DATA_PACK) {
				resultado.dataPacks.add(entrada);
			}
		}

		return resultado;
	}

	public static List<ArchivoLocalCF> obtenerArchivosAnalizables(Path carpetaInstancia) throws IOException {
		List<ArchivoLocalCF> ret = new ArrayList<ArchivoLocalCF>();

		agregarArchivosDeCarpeta(ret, carpetaInstancia, "mods", TipoArchivoCF.MOD, ".jar");
		agregarArchivosDeCarpeta(ret, carpetaInstancia, "resourcepacks", TipoArchivoCF.RESOURCE_PACK, ".zip");
		agregarArchivosDeCarpeta(ret, carpetaInstancia, "shaderpacks", TipoArchivoCF.SHADER_PACK, ".zip");
		agregarArchivosDeCarpeta(ret, carpetaInstancia, "datapacks", TipoArchivoCF.DATA_PACK, ".zip");

		return ret;
	}

	private static void agregarArchivosDeCarpeta(List<ArchivoLocalCF> lista, Path carpetaInstancia,
			String nombreCarpeta, TipoArchivoCF tipo, String extension) throws IOException {

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
				lista.add(new ArchivoLocalCF(p.toAbsolutePath().normalize(), relativa, tipo));
			});
		}
	}

	public static Json.Nodo solicitarCoincidenciasPorFingerprints(List<ArchivoLocalCF> archivos) throws IOException {
		Json.Nodo cuerpo = Json.crearObjeto();
		Json.Nodo fingerprints = cuerpo.obtener("fingerprints");

		for (ArchivoLocalCF archivo : archivos) {
			fingerprints.agregar(archivo.fingerprint);
		}

		String endpoint = ProveedorModsCurseForge.ENDPOINT;
		if (!endpoint.endsWith("/")) {
			endpoint += "/";
		}

		String url = endpoint + "v1/fingerprints/" + GAME_ID_MINECRAFT;
		return postJson(url, Json.escribir(cuerpo), ProveedorModsCurseForge.obtenerClaveApi());
	}

	private static Json.Nodo postJson(String url, String cuerpo, String claveApi) throws IOException {
		HttpURLConnection con = null;

		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(20000);
			con.setReadTimeout(30000);
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");

			if (claveApi != null && !claveApi.trim().isEmpty()
					&& !"usar_klauncher_endpoint_si_no_tienes_clave_api".equals(claveApi)) {
				con.setRequestProperty("x-api-key", claveApi.trim());
			}

			try (OutputStream os = con.getOutputStream()) {
				os.write(cuerpo.getBytes("UTF-8"));
			}

			int codigo = con.getResponseCode();
			InputStream is = codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream();
			String respuesta = leerCompleto(is);

			if (codigo < 200 || codigo >= 300) {
				throw new IOException("Error CurseForge HTTP " + codigo + ": " + respuesta);
			}

			return Json.leer(respuesta);
		} finally {
			if (con != null) {
				con.disconnect();
			}
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

	private static Map<Long, Json.Nodo> indexarCoincidenciasPorFingerprint(Json.Nodo respuesta) {
		Map<Long, Json.Nodo> ret = new LinkedHashMap<Long, Json.Nodo>();

		Json.Nodo exactMatches = respuesta.obtener("data").obtener("exactMatches");

		if (!exactMatches.esArreglo()) {
			return ret;
		}

		for (int i = 0; i < exactMatches.tamano(); i++) {
			Json.Nodo match = exactMatches.en(i);
			long fingerprint = obtenerLargoSeguro(match.obtener("file").obtener("fileFingerprint"), 0L);

			if (fingerprint == 0L) {
				fingerprint = obtenerLargoSeguro(match.obtener("fingerprint"), 0L);
			}

			if (fingerprint != 0L) {
				ret.put(Long.valueOf(fingerprint), match);
			}
		}

		return ret;
	}

	public static Json.Nodo crearEntradaTLauncherDesdeCoincidencia(ArchivoLocalCF archivo, Json.Nodo match) {
		Json.Nodo file = match.obtener("file");
		Json.Nodo mod = match.obtener("mod");

		Json.Nodo entrada = Json.crearObjeto();

		long modId = obtenerLargoSeguro(file.obtener("modId"), obtenerLargoSeguro(mod.obtener("id"), 0L));
		long fileId = obtenerLargoSeguro(file.obtener("id"), 0L);

		entrada.obtener("stateGameElement").poner("active");
		entrada.obtener("id").poner(modId);
		entrada.obtener("name").poner(obtenerCadenaPrimera(mod.obtener("name"), file.obtener("displayName"),
				archivo.archivo.getFileName().toString()));
		entrada.obtener("author").poner(obtenerAutor(mod));
		entrada.obtener("linkProject").poner(obtenerCadenaSeguro(mod.obtener("links").obtener("websiteUrl"), ""));
		entrada.obtener("favorite").poner(false);
		entrada.obtener("categories").poner(crearCategorias(mod.obtener("categories")));
		entrada.obtener("picture").poner(obtenerLargoSeguro(mod.obtener("logo").obtener("id"), 0L));
		entrada.obtener("pictures").poner(crearPictures(mod.obtener("screenshots")));
		entrada.obtener("lanName")
				.poner(obtenerCadenaSeguro(mod.obtener("slug"), limpiarSlug(archivo.archivo.getFileName().toString())));

		Json.Nodo version = entrada.obtener("version");
		version.obtener("id").poner(fileId);
		version.obtener("name").poner(obtenerCadenaPrimera(file.obtener("displayName"), file.obtener("fileName"),
				archivo.archivo.getFileName().toString()));
		version.obtener("updateDate").poner(parsearFechaCF(obtenerCadenaSeguro(file.obtener("fileDate"), null)));
		version.obtener("type").poner(obtenerTipoLanzamiento(file.obtener("releaseType")));
		version.obtener("gameVersionsDTO").poner(crearVersionesJuego(file.obtener("gameVersions")));

		Json.Nodo metadata = version.obtener("metadata");
		metadata.obtener("sha1").poner(obtenerSha1DesdeFile(file, archivo.sha1));
		metadata.obtener("size").poner(obtenerLargoSeguro(file.obtener("fileLength"), archivo.tamano));
		metadata.obtener("path").poner(archivo.rutaRelativa.toString().replace('\\', '/'));
		metadata.obtener("url").poner(crearUrlMetadata(archivo, modId, fileId));

		version.obtener("installed").poner(0L);
		version.obtener("available").poner(false);
		version.obtener("remove").poner(false);
		version.obtener("minecraftVersionTypes").poner(crearTiposMinecraft(file.obtener("gameVersions")));

		entrada.obtener("popularity").poner(5);
		entrada.obtener("downloadMonth").poner(0L);
		entrada.obtener("userInstall").poner(false);
		entrada.obtener("populateStatus").poner(false);
		entrada.obtener("dependencies").poner(crearDependencias(file.obtener("dependencies")));
		entrada.obtener("lastGameVersion").poner(crearUltimaVersionJuego(file.obtener("gameVersions")));
		entrada.obtener("updated").poner(parsearFechaCF(obtenerCadenaSeguro(mod.obtener("dateModified"), null)));
		entrada.obtener("update").poner(parsearFechaCF(obtenerCadenaSeguro(mod.obtener("dateModified"), null)));
		entrada.obtener("downloadALL").poner(obtenerLargoSeguro(mod.obtener("downloadCount"), 0L));
		entrada.obtener("available").poner(obtenerBooleanoSeguro(mod.obtener("isAvailable"), true));
		entrada.obtener("parser").poner(true);
		entrada.obtener("totalComments").poner(0);
		entrada.obtener("adult").poner(false);

		return entrada;
	}

	private static String crearUrlMetadata(ArchivoLocalCF archivo, long modId, long fileId) {
		String nombre = archivo.archivo.getFileName().toString();

		if (archivo.tipo == TipoArchivoCF.RESOURCE_PACK) {
			return "/resourcepacks/" + modId + "/" + fileId + "/" + nombre;
		}

		if (archivo.tipo == TipoArchivoCF.SHADER_PACK) {
			return "/shaderpacks/" + modId + "/" + fileId + "/" + nombre;
		}

		if (archivo.tipo == TipoArchivoCF.DATA_PACK) {
			return "/datapacks/" + modId + "/" + fileId + "/" + nombre;
		}

		return "/mods/" + modId + "/" + fileId + "/" + nombre;
	}

	private static Json.Nodo crearCategorias(Json.Nodo categoriasCF) {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		if (categoriasCF != null && categoriasCF.esArreglo()) {
			for (int i = 0; i < categoriasCF.tamano(); i++) {
				Json.Nodo c = categoriasCF.en(i);
				Json.Nodo out = Json.crearObjeto();

				out.obtener("id").poner(obtenerLargoSeguro(c.obtener("id"), 0L));
				out.obtener("name")
						.poner(obtenerCadenaSeguro(c.obtener("slug"), obtenerCadenaSeguro(c.obtener("name"), "")));
				out.obtener("shortName").poner(obtenerCadenaSeguro(c.obtener("slug"), ""));
				out.obtener("nesting").poner(0);

				arr.agregar(out);
			}
		}

		return obj.obtener("arr");
	}

	private static Json.Nodo crearPictures(Json.Nodo screenshots) {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		if (screenshots != null && screenshots.esArreglo()) {
			for (int i = 0; i < screenshots.tamano(); i++) {
				long id = obtenerLargoSeguro(screenshots.en(i).obtener("id"), 0L);
				if (id != 0L) {
					arr.agregar(id);
				}
			}
		}

		return obj.obtener("arr");
	}

	private static Json.Nodo crearVersionesJuego(Json.Nodo versionesCF) {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		if (versionesCF != null && versionesCF.esArreglo()) {
			for (int i = 0; i < versionesCF.tamano(); i++) {
				String nombre = versionesCF.en(i).comoCadena();

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

		return obj.obtener("arr");
	}

	private static Json.Nodo crearTiposMinecraft(Json.Nodo versionesCF) {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		if (versionesCF == null || !versionesCF.esArreglo()) {
			return arr;
		}

		boolean forge = false;
		boolean fabric = false;
		boolean quilt = false;
		boolean neoForge = false;

		for (int i = 0; i < versionesCF.tamano(); i++) {
			String v = versionesCF.en(i).comoCadena();
			if (v == null) {
				continue;
			}

			String l = v.toLowerCase();

			if (l.contains("forge") && !l.contains("neoforge")) {
				forge = true;
			}

			if (l.contains("fabric")) {
				fabric = true;
			}

			if (l.contains("quilt")) {
				quilt = true;
			}

			if (l.contains("neoforge")) {
				neoForge = true;
			}
		}

		if (forge) {
			arr.agregar(crearTipoMinecraft(1, "forge"));
		}

		if (fabric) {
			arr.agregar(crearTipoMinecraft(2, "fabric"));
		}

		if (quilt) {
			arr.agregar(crearTipoMinecraft(4, "quilt"));
		}

		if (neoForge) {
			arr.agregar(crearTipoMinecraft(5, "neoForge"));
		}

		return arr;
	}

	private static Json.Nodo crearTipoMinecraft(long id, String nombre) {
		Json.Nodo obj = Json.crearObjeto();
		obj.obtener("id").poner(id);
		obj.obtener("name").poner(nombre);
		return obj;
	}

	private static Json.Nodo crearDependencias(Json.Nodo dependenciasCF) {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		if (dependenciasCF != null && dependenciasCF.esArreglo()) {
			for (int i = 0; i < dependenciasCF.tamano(); i++) {
				Json.Nodo dep = dependenciasCF.en(i);
				Json.Nodo out = Json.crearObjeto();

				out.obtener("gameType").poner("mod");
				out.obtener("id").poner(obtenerLargoSeguro(dep.obtener("fileId"), 0L));
				out.obtener("name").poner("");
				out.obtener("gameEntityId").poner(obtenerLargoSeguro(dep.obtener("modId"), 0L));
				out.obtener("dependencyType").poner(tipoDependenciaCF(dep.obtener("relationType")));
				out.obtener("available").poner(false);

				arr.agregar(out);
			}
		}

		return arr;
	}

	private static Json.Nodo crearUltimaVersionJuego(Json.Nodo versionesCF) {
		Json.Nodo obj = Json.crearObjeto();

		if (versionesCF != null && versionesCF.esArreglo()) {
			for (int i = 0; i < versionesCF.tamano(); i++) {
				String nombre = versionesCF.en(i).comoCadena();

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

	public static long calcularFingerprintCurseForge(Path archivo) throws IOException {
		byte[] bytes = Files.readAllBytes(archivo);
		ByteArrayOutputStream filtrado = new ByteArrayOutputStream(bytes.length);

		for (int i = 0; i < bytes.length; i++) {
			int b = bytes[i] & 0xff;

			// CurseForge ignora espacios, tabuladores, CR y LF antes de MurmurHash2.
			if (b == 9 || b == 10 || b == 13 || b == 32) {
				continue;
			}

			filtrado.write(b);
		}

		return murmurHash2(filtrado.toByteArray(), 1);
	}

	private static long murmurHash2(byte[] data, int seed) {
		final long m = 0x5bd1e995L;
		final int r = 24;

		long h = (seed ^ data.length) & 0xffffffffL;
		int len = data.length;
		int i = 0;

		while (len >= 4) {
			long k = (data[i] & 0xffL) | ((data[i + 1] & 0xffL) << 8) | ((data[i + 2] & 0xffL) << 16)
					| ((data[i + 3] & 0xffL) << 24);

			k = (k * m) & 0xffffffffL;
			k ^= (k >>> r);
			k = (k * m) & 0xffffffffL;

			h = (h * m) & 0xffffffffL;
			h ^= k;

			i += 4;
			len -= 4;
		}

		switch (len) {
		case 3:
			h ^= (data[i + 2] & 0xffL) << 16;
		case 2:
			h ^= (data[i + 1] & 0xffL) << 8;
		case 1:
			h ^= (data[i] & 0xffL);
			h = (h * m) & 0xffffffffL;
		default:
			break;
		}

		h ^= (h >>> 13);
		h = (h * m) & 0xffffffffL;
		h ^= (h >>> 15);

		return h & 0xffffffffL;
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

	private static String obtenerSha1DesdeFile(Json.Nodo file, String sha1Local) {
		Json.Nodo hashes = file.obtener("hashes");

		if (hashes != null && hashes.esArreglo()) {
			for (int i = 0; i < hashes.tamano(); i++) {
				Json.Nodo h = hashes.en(i);
				String valor = obtenerCadenaSeguro(h.obtener("value"), null);
				int algo = obtenerEnteroSeguro(h.obtener("algo"), 0);

				if (algo == 1 && valor != null && !valor.isEmpty()) {
					return valor;
				}
			}
		}

		return sha1Local;
	}

	private static String obtenerAutor(Json.Nodo mod) {
		Json.Nodo autores = mod.obtener("authors");

		if (autores != null && autores.esArreglo() && autores.tamano() > 0) {
			return obtenerCadenaSeguro(autores.en(0).obtener("name"), "");
		}

		return "";
	}

	private static String obtenerTipoLanzamiento(Json.Nodo nodo) {
		int tipo = obtenerEnteroSeguro(nodo, 1);

		if (tipo == 1) {
			return "release";
		}

		if (tipo == 2) {
			return "beta";
		}

		if (tipo == 3) {
			return "alpha";
		}

		return "release";
	}

	private static String tipoDependenciaCF(Json.Nodo nodo) {
		int tipo = obtenerEnteroSeguro(nodo, 0);

		if (tipo == 3) {
			return "required";
		}

		if (tipo == 2) {
			return "optional";
		}

		if (tipo == 5) {
			return "incompatible";
		}

		return "optional";
	}

	private static long parsearFechaCF(String fecha) {
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

	private static int obtenerEnteroSeguro(Json.Nodo nodo, int def) {
		try {
			return nodo.comoEntero();
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