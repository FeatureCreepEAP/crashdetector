package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.dto.modpack.curseforge.CFModDesdeJar;
import com.asbestosstar.crashdetector.dto.modpack.curseforge.ProveedorModsCurseForge;
import com.asbestosstar.crashdetector.dto.modpack.modrinth.MRModDesdeJar;
import com.asbestosstar.crashdetector.json.Json;

public class ColaActualizacionesModpack {

	public static class ActualizacionPendiente {
		public String fuente; // curseforge, modrinth, ambos
		public String loader;
		public String gameVersion;

		public Path archivoActual;
		public String rutaRelativa;
		public String nombreActual;
		public String nombreNuevo;

		public long curseForgeProjectIdActual;
		public long curseForgeFileIdActual;
		public long curseForgeProjectIdNuevo;
		public long curseForgeFileIdNuevo;

		public String modrinthProjectIdActual;
		public String modrinthVersionIdActual;
		public String modrinthProjectIdNuevo;
		public String modrinthVersionIdNuevo;

		public String sha1Actual;
		public String sha1Nuevo;
		public long tamanoNuevo;
		public String urlDescarga;

		public Json.Nodo entradaJsonActual;
		public Json.Nodo versionJsonNuevaCF;
		public Json.Nodo versionJsonNuevaMR;

		public boolean tieneActualizacion() {
			return urlDescarga != null && !urlDescarga.trim().isEmpty() && nombreNuevo != null
					&& !nombreNuevo.trim().isEmpty() && !nombreNuevo.equals(nombreActual);
		}
	}

	public static List<ActualizacionPendiente> crearColaCarpetaActual() throws IOException {
		return crearCola(Paths.get(".").toAbsolutePath().normalize());
	}

	public static List<ActualizacionPendiente> crearCola(Path carpetaInstancia) throws IOException {
		carpetaInstancia = carpetaInstancia.toAbsolutePath().normalize();

		ActualizadorTLauncherAdditional.actualizar(carpetaInstancia);

		Path archivoJson = carpetaInstancia.resolve(ActualizadorTLauncherAdditional.NOMBRE_ARCHIVO);
		if (!Files.isRegularFile(archivoJson)) {
			throw new IOException(
					"No existe " + ActualizadorTLauncherAdditional.NOMBRE_ARCHIVO + " en " + carpetaInstancia);
		}

		Json.Nodo raiz = Json.leer(new String(Files.readAllBytes(archivoJson), StandardCharsets.UTF_8));
		Json.Nodo version = raiz.obtener("modpack").obtener("version");

		String loader = obtenerCadenaSeguro(version.obtener("loader"), "");
		String gameVersion = obtenerCadenaSeguro(version.obtener("gameVersion"), "");

		List<ActualizacionPendiente> ret = Collections.synchronizedList(new ArrayList<ActualizacionPendiente>());

		int hilos = Math.max(1, Runtime.getRuntime().availableProcessors() * 2);
		ExecutorService executor = Executors.newFixedThreadPool(hilos);
		List<Future<?>> futuros = new ArrayList<Future<?>>();

		agregarTrabajosActualizaciones(futuros, executor, ret, carpetaInstancia, version.obtener("mods"), loader,
				gameVersion);
		agregarTrabajosActualizaciones(futuros, executor, ret, carpetaInstancia, version.obtener("resourcePacks"),
				loader, gameVersion);
		agregarTrabajosActualizaciones(futuros, executor, ret, carpetaInstancia, version.obtener("shaderPacks"), loader,
				gameVersion);
		agregarTrabajosActualizaciones(futuros, executor, ret, carpetaInstancia, version.obtener("dataPacks"), loader,
				gameVersion);

		executor.shutdown();

		for (Future<?> futuro : futuros) {
			try {
				futuro.get();
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		return new ArrayList<ActualizacionPendiente>(ret);
	}

	private static void agregarTrabajosActualizaciones(List<Future<?>> futuros, ExecutorService executor,
			final List<ActualizacionPendiente> ret, final Path carpetaInstancia, Json.Nodo arreglo, final String loader,
			final String gameVersion) {

		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			final Json.Nodo entrada = arreglo.en(i);

			futuros.add(executor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						ActualizacionPendiente act = crearActualizacionParaEntrada(carpetaInstancia, entrada, loader,
								gameVersion);

						if (act != null && act.tieneActualizacion()) {
							ret.add(act);
						}
					} catch (Throwable t) {
						CrashDetectorLogger.logException(t);
					}
				}
			}));
		}
	}

	private static void agregarActualizacionesDeArreglo(List<ActualizacionPendiente> ret, Path carpetaInstancia,
			Json.Nodo arreglo, String loader, String gameVersion) {

		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);

			try {
				ActualizacionPendiente act = crearActualizacionParaEntrada(carpetaInstancia, entrada, loader,
						gameVersion);

				if (act != null && act.tieneActualizacion()) {
					ret.add(act);
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}
	}

	private static ActualizacionPendiente crearActualizacionParaEntrada(Path carpetaInstancia, Json.Nodo entrada,
			String loader, String gameVersion) throws IOException {

		ActualizacionPendiente act = new ActualizacionPendiente();
		act.loader = loader;
		act.gameVersion = gameVersion;
		act.entradaJsonActual = entrada;

		Json.Nodo versionActual = entrada.obtener("version");
		Json.Nodo metadata = versionActual.obtener("metadata");

		act.rutaRelativa = obtenerCadenaSeguro(metadata.obtener("path"), "");
		act.nombreActual = nombreArchivo(act.rutaRelativa);
		act.archivoActual = carpetaInstancia.resolve(act.rutaRelativa).toAbsolutePath().normalize();

		act.sha1Actual = obtenerCadenaSeguro(metadata.obtener("sha1"), "");

		act.curseForgeProjectIdActual = obtenerLargoSeguro(entrada.obtener("id"), 0L);
		act.curseForgeFileIdActual = obtenerLargoSeguro(versionActual.obtener("id"), 0L);

		act.modrinthProjectIdActual = obtenerCadenaSeguro(entrada.obtener("modrinthProjectId"), "");
		act.modrinthVersionIdActual = obtenerCadenaSeguro(versionActual.obtener("modrinthVersionId"), "");

		ActualizacionPendiente cf = buscarActualizacionCurseForge(act);
		ActualizacionPendiente mr = buscarActualizacionModrinth(act);

		if (cf == null && mr == null) {
			return null;
		}

		if (cf != null && mr != null) {
			copiarDatosNuevos(act, cf);
			act.fuente = "ambos";

			act.modrinthProjectIdNuevo = mr.modrinthProjectIdNuevo;
			act.modrinthVersionIdNuevo = mr.modrinthVersionIdNuevo;
			act.versionJsonNuevaMR = mr.versionJsonNuevaMR;

			return act;
		}

		if (mr != null) {
			copiarDatosNuevos(act, mr);
			act.fuente = "modrinth";
			return act;
		}

		copiarDatosNuevos(act, cf);
		act.fuente = "curseforge";
		return act;
	}

	private static ActualizacionPendiente buscarActualizacionCurseForge(ActualizacionPendiente base) {
		try {
			if (base.curseForgeProjectIdActual <= 0L || base.curseForgeFileIdActual <= 0L) {
				return null;
			}

			Json.Nodo respuesta = solicitarArchivosCurseForge(base.curseForgeProjectIdActual, base.loader,
					base.gameVersion);

			Json.Nodo data = respuesta.obtener("data");
			if (data == null || !data.esArreglo()) {
				return null;
			}

			Json.Nodo mejor = null;
			long mejorFecha = 0L;

			for (int i = 0; i < data.tamano(); i++) {
				Json.Nodo file = data.en(i);

				long fileId = obtenerLargoSeguro(file.obtener("id"), 0L);
				if (fileId <= 0L) {
					continue;
				}

				if (fileId == base.curseForgeFileIdActual) {
					continue;
				}

				String downloadUrl = obtenerCadenaSeguro(file.obtener("downloadUrl"), "");
				if (downloadUrl == null || downloadUrl.trim().isEmpty()) {
					continue;
				}

				String fileName = obtenerCadenaSeguro(file.obtener("fileName"), "");
				if (fileName == null || fileName.trim().isEmpty()) {
					continue;
				}

				if (!coincideGameVersionCurseForge(file, base.gameVersion)) {
					continue;
				}

				if (!coincideLoaderCurseForge(file, base.loader)) {
					continue;
				}

				long fecha = parsearFecha(obtenerCadenaSeguro(file.obtener("fileDate"), null));
				if (mejor == null || fecha > mejorFecha) {
					mejor = file;
					mejorFecha = fecha;
				}
			}

			if (mejor == null) {
				return null;
			}

			ActualizacionPendiente act = new ActualizacionPendiente();
			act.curseForgeProjectIdNuevo = obtenerLargoSeguro(mejor.obtener("modId"), base.curseForgeProjectIdActual);
			act.curseForgeFileIdNuevo = obtenerLargoSeguro(mejor.obtener("id"), 0L);
			act.nombreNuevo = obtenerCadenaSeguro(mejor.obtener("fileName"), "");
			act.urlDescarga = obtenerCadenaSeguro(mejor.obtener("downloadUrl"), "");
			act.sha1Nuevo = obtenerSha1CurseForge(mejor, "");
			act.tamanoNuevo = obtenerLargoSeguro(mejor.obtener("fileLength"), 0L);
			act.versionJsonNuevaCF = mejor;

			return act;
		} catch (Throwable t) {
			return null;
		}
	}

	private static ActualizacionPendiente buscarActualizacionModrinth(ActualizacionPendiente base) {
		try {
			if (base.sha1Actual == null || base.sha1Actual.trim().isEmpty()) {
				return null;
			}

			Json.Nodo nueva = solicitarUltimaVersionModrinth(base.sha1Actual, base.loader, base.gameVersion);
			Json.Nodo archivo = obtenerArchivoPrimarioModrinth(nueva);

			String nombreNuevo = obtenerCadenaSeguro(archivo.obtener("filename"), "");
			String url = obtenerCadenaSeguro(archivo.obtener("url"), "");

			if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
				return null;
			}

			if (url == null || url.trim().isEmpty()) {
				return null;
			}

			String versionId = obtenerCadenaSeguro(nueva.obtener("id"), "");
			if (versionId.equals(base.modrinthVersionIdActual)) {
				return null;
			}

			ActualizacionPendiente act = new ActualizacionPendiente();
			act.modrinthProjectIdNuevo = obtenerCadenaSeguro(nueva.obtener("project_id"), "");
			act.modrinthVersionIdNuevo = versionId;
			act.nombreNuevo = nombreNuevo;
			act.urlDescarga = url;
			act.sha1Nuevo = obtenerCadenaSeguro(archivo.obtener("hashes").obtener("sha1"), "");
			act.tamanoNuevo = obtenerLargoSeguro(archivo.obtener("size"), 0L);
			act.versionJsonNuevaMR = nueva;
			return act;
		} catch (Throwable t) {
			return null;
		}
	}

	public static int activarCola(Path carpetaInstancia, List<ActualizacionPendiente> cola) throws IOException {
		carpetaInstancia = carpetaInstancia.toAbsolutePath().normalize();

		if (cola == null || cola.isEmpty()) {
			return 0;
		}

		Path archivoJson = carpetaInstancia.resolve(ActualizadorTLauncherAdditional.NOMBRE_ARCHIVO);
		if (!Files.isRegularFile(archivoJson)) {
			throw new IOException(
					"No existe " + ActualizadorTLauncherAdditional.NOMBRE_ARCHIVO + " en " + carpetaInstancia);
		}

		Json.Nodo raiz = Json.leer(new String(Files.readAllBytes(archivoJson), StandardCharsets.UTF_8));

		int aplicadas = 0;

		for (ActualizacionPendiente act : cola) {
			try {
				if (activarActualizacion(carpetaInstancia, raiz, act)) {
					aplicadas++;
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		if (aplicadas > 0) {
			Files.write(archivoJson, Json.escribir(raiz).getBytes(StandardCharsets.UTF_8));
		}

		return aplicadas;
	}

	private static boolean activarActualizacion(Path carpetaInstancia, Json.Nodo raiz, ActualizacionPendiente act)
			throws IOException {

		if (act == null || !act.tieneActualizacion()) {
			return false;
		}

		Path archivoViejo = act.archivoActual;
		if (archivoViejo == null) {
			archivoViejo = carpetaInstancia.resolve(act.rutaRelativa).toAbsolutePath().normalize();
		}

		if (!Files.isRegularFile(archivoViejo)) {
			return false;
		}

		Path archivoNuevo = archivoViejo.getParent().resolve(act.nombreNuevo).toAbsolutePath().normalize();

		descargarArchivo(act.urlDescarga, archivoNuevo);

		if (act.sha1Nuevo != null && !act.sha1Nuevo.trim().isEmpty()) {
			String sha1Local = CFModDesdeJar.calcularSha1(archivoNuevo);
			if (!act.sha1Nuevo.equalsIgnoreCase(sha1Local)) {
				Files.deleteIfExists(archivoNuevo);
				throw new IOException("SHA-1 incorrecto para " + archivoNuevo);
			}
		}

		Files.deleteIfExists(archivoViejo);

		String rutaNueva = carpetaInstancia.relativize(archivoNuevo).toString().replace('\\', '/');
		actualizarEntradaJson(raiz, act, rutaNueva, archivoNuevo);

		return true;
	}

	private static void actualizarEntradaJson(Json.Nodo raiz, ActualizacionPendiente act, String rutaNueva,
			Path archivoNuevo) throws IOException {

		Json.Nodo entrada = buscarEntradaPorRuta(raiz, act.rutaRelativa);
		if (entrada == null) {
			return;
		}

		Json.Nodo version = entrada.obtener("version");
		Json.Nodo metadata = version.obtener("metadata");

		metadata.obtener("path").poner(rutaNueva);
		metadata.obtener("sha1").poner(CFModDesdeJar.calcularSha1(archivoNuevo));
		metadata.obtener("size").poner(Files.size(archivoNuevo));
		metadata.obtener("url").poner(act.urlDescarga);

		if (act.curseForgeProjectIdNuevo > 0L) {
			entrada.obtener("id").poner(act.curseForgeProjectIdNuevo);
		}

		if (act.curseForgeFileIdNuevo > 0L) {
			version.obtener("id").poner(act.curseForgeFileIdNuevo);
		}

		if (act.nombreNuevo != null && !act.nombreNuevo.trim().isEmpty()) {
			version.obtener("name").poner(act.nombreNuevo);
		}

		if (act.modrinthProjectIdNuevo != null && !act.modrinthProjectIdNuevo.trim().isEmpty()) {
			entrada.obtener("modrinthProjectId").poner(act.modrinthProjectIdNuevo);
		}

		if (act.modrinthVersionIdNuevo != null && !act.modrinthVersionIdNuevo.trim().isEmpty()) {
			version.obtener("modrinthVersionId").poner(act.modrinthVersionIdNuevo);
		}
	}

	private static Json.Nodo buscarEntradaPorRuta(Json.Nodo raiz, String rutaRelativa) {
		Json.Nodo version = raiz.obtener("modpack").obtener("version");

		Json.Nodo ret = buscarEntradaPorRutaEnArreglo(version.obtener("mods"), rutaRelativa);
		if (ret != null)
			return ret;

		ret = buscarEntradaPorRutaEnArreglo(version.obtener("resourcePacks"), rutaRelativa);
		if (ret != null)
			return ret;

		ret = buscarEntradaPorRutaEnArreglo(version.obtener("shaderPacks"), rutaRelativa);
		if (ret != null)
			return ret;

		return buscarEntradaPorRutaEnArreglo(version.obtener("dataPacks"), rutaRelativa);
	}

	private static Json.Nodo buscarEntradaPorRutaEnArreglo(Json.Nodo arreglo, String rutaRelativa) {
		if (arreglo == null || !arreglo.esArreglo()) {
			return null;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);
			String path = obtenerCadenaSeguro(entrada.obtener("version").obtener("metadata").obtener("path"), "");

			if (rutaRelativa.equals(path)) {
				return entrada;
			}
		}

		return null;
	}

	private static Json.Nodo solicitarArchivosCurseForge(long modId, String loader, String gameVersion)
			throws IOException {

		String endpoint = ProveedorModsCurseForge.ENDPOINT;
		if (!endpoint.endsWith("/")) {
			endpoint += "/";
		}

		StringBuilder url = new StringBuilder(endpoint);
		url.append("v1/mods/").append(modId).append("/files?pageSize=50");

		if (gameVersion != null && !gameVersion.trim().isEmpty()) {
			url.append("&gameVersion=").append(URLEncoder.encode(gameVersion, "UTF-8"));
		}

		int loaderCF = loaderCurseForge(loader);
		if (loaderCF > 0 && gameVersion != null && !gameVersion.trim().isEmpty()) {
			url.append("&modLoaderType=").append(loaderCF);
		}

		return getJson(url.toString(), ProveedorModsCurseForge.obtenerClaveApi());
	}

	private static Json.Nodo solicitarUltimaVersionModrinth(String sha1, String loader, String gameVersion)
			throws IOException {

		String url = MRModDesdeJar.ENDPOINT_MODRINTH + "/version_file/" + URLEncoder.encode(sha1, "UTF-8")
				+ "/update?algorithm=sha1";

		Json.Nodo cuerpo = Json.crearObjeto();
		Json.Nodo loaders = cuerpo.obtener("loaders");
		Json.Nodo gameVersions = cuerpo.obtener("game_versions");

		String loaderMR = loaderModrinth(loader);
		if (loaderMR == null || loaderMR.trim().isEmpty()) {
			loaderMR = "minecraft";
		}

		loaders.agregar(loaderMR);

		if (gameVersion != null && !gameVersion.trim().isEmpty()) {
			gameVersions.agregar(gameVersion);
		}

		return postJson(url, Json.escribir(cuerpo), null);
	}

	private static void descargarArchivo(String url, Path destino) throws IOException {
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

			if (destino.getParent() != null) {
				Files.createDirectories(destino.getParent());
			}

			try (InputStream is = con.getInputStream()) {
				Files.copy(is, destino, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			}
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
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
			String respuesta = leerCompleto(
					codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream());

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

	private static Json.Nodo postJson(String url, String cuerpo, String claveApi) throws IOException {
		HttpURLConnection con = null;

		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setConnectTimeout(20000);
			con.setReadTimeout(30000);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("User-Agent", "CrashDetectorMC/1.0");

			if (claveApi != null && !claveApi.trim().isEmpty()) {
				con.setRequestProperty("x-api-key", claveApi.trim());
			}

			try (OutputStream os = con.getOutputStream()) {
				os.write(cuerpo.getBytes(StandardCharsets.UTF_8));
			}

			int codigo = con.getResponseCode();
			String respuesta = leerCompleto(
					codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream());

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

	private static void copiarDatosNuevos(ActualizacionPendiente dst, ActualizacionPendiente src) {
		dst.nombreNuevo = src.nombreNuevo;
		dst.urlDescarga = src.urlDescarga;
		dst.sha1Nuevo = src.sha1Nuevo;
		dst.tamanoNuevo = src.tamanoNuevo;

		dst.curseForgeProjectIdNuevo = src.curseForgeProjectIdNuevo;
		dst.curseForgeFileIdNuevo = src.curseForgeFileIdNuevo;

		dst.modrinthProjectIdNuevo = src.modrinthProjectIdNuevo;
		dst.modrinthVersionIdNuevo = src.modrinthVersionIdNuevo;

		dst.versionJsonNuevaCF = src.versionJsonNuevaCF;
		dst.versionJsonNuevaMR = src.versionJsonNuevaMR;
	}

	private static Json.Nodo obtenerArchivoPrimarioModrinth(Json.Nodo version) {
		Json.Nodo files = version.obtener("files");

		if (files != null && files.esArreglo() && files.tamano() > 0) {
			for (int i = 0; i < files.tamano(); i++) {
				Json.Nodo f = files.en(i);
				if (obtenerBooleanoSeguro(f.obtener("primary"), false)) {
					return f;
				}
			}

			return files.en(0);
		}

		return Json.crearObjeto();
	}

	private static String obtenerSha1CurseForge(Json.Nodo file, String def) {
		Json.Nodo hashes = file.obtener("hashes");

		if (hashes != null && hashes.esArreglo()) {
			for (int i = 0; i < hashes.tamano(); i++) {
				Json.Nodo h = hashes.en(i);
				int algo = obtenerEnteroSeguro(h.obtener("algo"), 0);
				String valor = obtenerCadenaSeguro(h.obtener("value"), "");

				if (algo == 1 && !valor.isEmpty()) {
					return valor;
				}
			}
		}

		return def;
	}

	private static int loaderCurseForge(String loader) {
		if (loader == null)
			return 0;

		String l = loader.toLowerCase();

		if ("minecraftforge".equals(l) || "forge".equals(l))
			return 1;
		if ("liteloader".equals(l))
			return 3;
		if ("fabric".equals(l))
			return 4;
		if ("quilt".equals(l))
			return 5;
		if ("neoforge".equals(l))
			return 6;

		return 0;
	}

	private static String loaderModrinth(String loader) {
		if (loader == null)
			return "";

		String l = loader.toLowerCase();

		if ("minecraftforge".equals(l) || "forge".equals(l))
			return "forge";
		if ("fabric".equals(l))
			return "fabric";
		if ("quilt".equals(l))
			return "quilt";
		if ("neoforge".equals(l))
			return "neoforge";
		if ("liteloader".equals(l))
			return "liteloader";

		return l;
	}

	private static String nombreArchivo(String path) {
		if (path == null)
			return "";

		String limpio = path.replace('\\', '/');
		int idx = limpio.lastIndexOf('/');

		return idx >= 0 && idx + 1 < limpio.length() ? limpio.substring(idx + 1) : limpio;
	}

	private static String leerCompleto(InputStream is) throws IOException {
		if (is == null)
			return "";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int leidos;

		while ((leidos = is.read(buffer)) != -1) {
			baos.write(buffer, 0, leidos);
		}

		return new String(baos.toByteArray(), StandardCharsets.UTF_8);
	}

	private static long parsearFecha(String fecha) {
		try {
			if (fecha == null || fecha.trim().isEmpty())
				return 0L;
			return java.time.Instant.parse(fecha).toEpochMilli();
		} catch (Throwable t) {
			return 0L;
		}
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

	private static boolean coincideGameVersion(Json.Nodo file, String gameVersion) {
		Json.Nodo versions = file.obtener("gameVersions");
		if (versions == null || !versions.esArreglo())
			return true;

		for (int i = 0; i < versions.tamano(); i++) {
			String v = obtenerCadenaSeguro(versions.en(i), "");
			if (v.equalsIgnoreCase(gameVersion))
				return true;
		}

		return false;
	}

	private static boolean coincideLoader(Json.Nodo file, String loader) {
		int esperado = loaderCurseForge(loader);

		Json.Nodo loaders = file.obtener("sortableGameVersions");
		if (loaders == null || !loaders.esArreglo())
			return true;

		for (int i = 0; i < loaders.tamano(); i++) {
			int tipo = obtenerEnteroSeguro(loaders.en(i).obtener("gameVersionTypeId"), 0);
			if (tipo == esperado)
				return true;
		}

		return false;
	}

	private static boolean coincideGameVersionCurseForge(Json.Nodo file, String gameVersion) {
		if (gameVersion == null || gameVersion.trim().isEmpty()) {
			return true;
		}

		String esperado = gameVersion.trim();

		Json.Nodo gameVersions = file.obtener("gameVersions");
		if (gameVersions != null && gameVersions.esArreglo()) {
			for (int i = 0; i < gameVersions.tamano(); i++) {
				String v = obtenerCadenaSeguro(gameVersions.en(i), "");
				if (esperado.equalsIgnoreCase(v.trim())) {
					return true;
				}
			}
		}

		Json.Nodo sortable = file.obtener("sortableGameVersions");
		if (sortable != null && sortable.esArreglo()) {
			for (int i = 0; i < sortable.tamano(); i++) {
				String v1 = obtenerCadenaSeguro(sortable.en(i).obtener("gameVersion"), "");
				String v2 = obtenerCadenaSeguro(sortable.en(i).obtener("gameVersionName"), "");

				if (esperado.equalsIgnoreCase(v1.trim()) || esperado.equalsIgnoreCase(v2.trim())) {
					return true;
				}
			}
		}

		return false;
	}

	private static boolean coincideLoaderCurseForge(Json.Nodo file, String loader) {
		int esperado = loaderCurseForge(loader);

		if (esperado <= 0) {
			return true;
		}

		Json.Nodo sortable = file.obtener("sortableGameVersions");
		if (sortable == null || !sortable.esArreglo()) {
			return false;
		}

		for (int i = 0; i < sortable.tamano(); i++) {
			int tipo = obtenerEnteroSeguro(sortable.en(i).obtener("gameVersionTypeId"), 0);

			if (tipo == esperado) {
				return true;
			}
		}

		return false;
	}

}