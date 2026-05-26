package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.curseforge.CFModDesdeJar;
import com.asbestosstar.crashdetector.dto.modpack.modrinth.MRModDesdeJar;

/**
 * Actualiza TLauncherAdditional.json en la carpeta actual.
 *
 * Este archivo se trata como el manifiesto principal del modpack. Otros
 * exportadores deben basarse en este archivo, no reconstruir la metadata desde
 * cero.
 *
 * Reglas: - Si no existe, lo crea. - Si existe, conserva los datos existentes
 * cuando sea posible. - Agrega metadata principal: loader, loaderVersion,
 * gameVersion, libraries y additionalFiles. - Fuerza que el sistema de skins no
 * se active. - Si ya existe una entrada para el mismo jar/zip, no la reemplaza.
 * - Si una entrada apunta a un jar/zip que ya no existe, la elimina. - Si hay
 * un jar/zip nuevo, lo consulta con CurseForge y lo agrega. - No toca
 * TLauncherAdditional.json si estamos dentro de:
 * minecraft/versions/<instancia>/ o .minecraft/versions/<instancia>/
 */
public class ActualizadorTLauncherAdditional {

	public static final String NOMBRE_ARCHIVO = "TLauncherAdditional.json";

	public static ResultadoActualizacion actualizarCarpetaActual() throws IOException {
		return actualizar(Paths.get(".").toAbsolutePath().normalize());
	}

	public static ResultadoActualizacion actualizar(Path carpetaInstancia) throws IOException {
		carpetaInstancia = carpetaInstancia.toAbsolutePath().normalize();

		boolean dentroDeInstanciaTLauncher = estaDentroDeCarpetaVersionesTLauncher(carpetaInstancia);

		Path archivoJson = carpetaInstancia.resolve(NOMBRE_ARCHIVO);

		Json.Nodo raiz = Files.exists(archivoJson) ? leerJsonSeguro(archivoJson)
				: crearJsonBase(
						carpetaInstancia.getFileName() != null ? carpetaInstancia.getFileName().toString() : "Modpack");

		/*
		 * Si estamos dentro de una instancia real de TLauncher, NO tocamos la metadata
		 * principal del launcher, loader, libraries, additionalFiles, skins, etc.
		 *
		 * Pero SÍ seguimos actualizando la lista de mods/recurso/shaders/datapacks: -
		 * eliminar entradas cuyo archivo ya no existe; - agregar archivos nuevos; -
		 * completar IDs de Modrinth por SHA-1.
		 *
		 * Esto permite que CrashDetector mantenga TLauncherAdditional.json útil como
		 * formato base sin romper una instancia creada por TLauncher.
		 */
		if (!dentroDeInstanciaTLauncher) {
			GeneradorManifiestoTLauncherAdditional.completarManifiestoPrincipal(raiz, carpetaInstancia);
		} else {
			CrashDetectorLogger.log(
					"Instancia TLauncher detectada. Se conserva metadata principal, pero se actualizan entradas de mods: "
							+ carpetaInstancia);
			asegurarEstructuraBasicaModpack(raiz);
		}

		List<CFModDesdeJar.ArchivoLocalCF> archivosLocales = CFModDesdeJar.obtenerArchivosAnalizables(carpetaInstancia);

		Map<CFModDesdeJar.TipoArchivoCF, Set<String>> nombresLocalesPorTipo = crearIndiceNombresLocales(
				archivosLocales);
		Map<CFModDesdeJar.TipoArchivoCF, Set<String>> nombresExistentesPorTipo = new HashMap<CFModDesdeJar.TipoArchivoCF, Set<String>>();

		int removidos = 0;
		removidos += filtrarEntradasExistentes(raiz, "mods", CFModDesdeJar.TipoArchivoCF.MOD, nombresLocalesPorTipo,
				nombresExistentesPorTipo);
		removidos += filtrarEntradasExistentes(raiz, "resourcePacks", CFModDesdeJar.TipoArchivoCF.RESOURCE_PACK,
				nombresLocalesPorTipo, nombresExistentesPorTipo);
		removidos += filtrarEntradasExistentes(raiz, "shaderPacks", CFModDesdeJar.TipoArchivoCF.SHADER_PACK,
				nombresLocalesPorTipo, nombresExistentesPorTipo);
		removidos += filtrarEntradasExistentes(raiz, "dataPacks", CFModDesdeJar.TipoArchivoCF.DATA_PACK,
				nombresLocalesPorTipo, nombresExistentesPorTipo);

		List<CFModDesdeJar.ArchivoLocalCF> archivosNuevos = obtenerArchivosNuevos(archivosLocales,
				nombresExistentesPorTipo);

		prepararFingerprintsMulticore(archivosNuevos);

		CFModDesdeJar.ResultadoCF resultadoCF = archivosNuevos.isEmpty() ? new CFModDesdeJar.ResultadoCF()
				: analizarArchivosYaPreparados(archivosNuevos);

		int agregados = 0;
		agregados += agregarEntradasNuevas(raiz, "mods", resultadoCF.mods);
		agregados += agregarEntradasNuevas(raiz, "resourcePacks", resultadoCF.resourcePacks);
		agregados += agregarEntradasNuevas(raiz, "shaderPacks", resultadoCF.shaderPacks);
		agregados += agregarEntradasNuevas(raiz, "dataPacks", resultadoCF.dataPacks);

		/*
		 * Esto debe ejecutarse SIEMPRE, incluso dentro de una instancia TLauncher. Los
		 * exportadores de Modrinth, Packwiz y mrpack necesitan IDs reales de
		 * proyecto/versión, no solo los IDs CRC/CF.
		 */
		int idsNormalesAgregados = completarIdsCurseForgeFaltantes(raiz, archivosLocales);
		int idsModrinthAgregados = completarIdsModrinthFaltantes(raiz);
		int gitignoreAgregados = actualizarGitignoreConArchivosDescargables(raiz, carpetaInstancia);

		Files.write(archivoJson, Json.escribir(raiz).getBytes(StandardCharsets.UTF_8));

		return new ResultadoActualizacion(false,
				agregados + idsNormalesAgregados + idsModrinthAgregados + gitignoreAgregados, removidos,
				resultadoCF.sinCoincidencia.size(), archivoJson);
	}

	private static int actualizarGitignoreConArchivosDescargables(Json.Nodo raiz, Path carpetaInstancia) {
		try {
			Set<String> rutas = obtenerRutasDescargablesParaGitignore(raiz);

			if (rutas.isEmpty()) {
				return 0;
			}

			Path gitignore = carpetaInstancia.resolve(".gitignore").toAbsolutePath().normalize();

			List<String> lineas = Files.isRegularFile(gitignore) ? Files.readAllLines(gitignore, StandardCharsets.UTF_8)
					: new ArrayList<String>();

			Set<String> yaActivas = new HashSet<String>();

			for (String linea : lineas) {
				String limpia = linea == null ? "" : linea.trim();

				if (limpia.isEmpty()) {
					continue;
				}

				// Si está comentada, NO cuenta como activa.
				if (limpia.startsWith("#")) {
					continue;
				}

				yaActivas.add(normalizarLineaGitignore(limpia));
			}

			int agregados = 0;

			for (String ruta : rutas) {
				String regla = "/" + ruta.replace('\\', '/');

				if (yaActivas.contains(normalizarLineaGitignore(regla))) {
					continue;
				}

				lineas.add(regla);
				yaActivas.add(normalizarLineaGitignore(regla));
				agregados++;
			}

			if (agregados > 0) {
				Files.write(gitignore, lineas, StandardCharsets.UTF_8);
			}

			return agregados;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return 0;
		}
	}

	private static Set<String> obtenerRutasDescargablesParaGitignore(Json.Nodo raiz) {
		Set<String> ret = new HashSet<String>();

		Json.Nodo version = raiz.obtener("modpack").obtener("version");

		agregarRutasDescargablesParaGitignore(ret, version.obtener("mods"));
		agregarRutasDescargablesParaGitignore(ret, version.obtener("resourcePacks"));
		agregarRutasDescargablesParaGitignore(ret, version.obtener("shaderPacks"));
		agregarRutasDescargablesParaGitignore(ret, version.obtener("dataPacks"));

		return ret;
	}

	private static void agregarRutasDescargablesParaGitignore(Set<String> ret, Json.Nodo arreglo) {
		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);

			if (!entradaDebeIrEnGitignore(entrada)) {
				continue;
			}

			String path = obtenerCadenaSeguro(entrada.obtener("version").obtener("metadata").obtener("path"), "");

			if (path == null || path.trim().isEmpty()) {
				continue;
			}

			path = path.replace('\\', '/');

			if (!rutaSeguraGitignore(path)) {
				continue;
			}

			ret.add(path);
		}
	}

	private static boolean entradaDebeIrEnGitignore(Json.Nodo entrada) {
		long projectId = obtenerLargoSeguro(entrada.obtener("id"), 0L);
		long versionId = obtenerLargoSeguro(entrada.obtener("version").obtener("id"), 0L);

		/*
		 * Solo cuentan IDs normales numéricos. Los IDs Modrinth NO cuentan aquí porque
		 * van en: entrada.modrinthProjectId entrada.version.modrinthVersionId
		 */
		if (projectId > 0L && versionId > 0L) {
			return true;
		}

		String url = obtenerCadenaSeguro(entrada.obtener("version").obtener("metadata").obtener("url"), "");

		return url != null && esUrlValidaDescargable(url);
	}

	private static boolean esUrlValidaDescargable(String url) {
		if (url == null) {
			return false;
		}

		String u = url.trim().toLowerCase();

		if (u.isEmpty()) {
			return false;
		}

		return u.startsWith("http://") || u.startsWith("https://");
	}

	private static boolean rutaSeguraGitignore(String ruta) {
		if (ruta == null) {
			return false;
		}

		String r = ruta.replace('\\', '/');

		if (r.trim().isEmpty()) {
			return false;
		}

		if (r.startsWith("/") || r.contains("../") || r.equals("..") || r.contains(":/")) {
			return false;
		}

		return true;
	}

	private static String normalizarLineaGitignore(String linea) {
		if (linea == null) {
			return "";
		}

		String l = linea.trim().replace('\\', '/');

		while (l.startsWith("./")) {
			l = l.substring(2);
		}

		while (l.startsWith("/")) {
			l = l.substring(1);
		}

		return l;
	}

	private static int completarIdsCurseForgeFaltantes(Json.Nodo raiz,
			List<CFModDesdeJar.ArchivoLocalCF> archivosLocales) {
		if (archivosLocales == null || archivosLocales.isEmpty()) {
			return 0;
		}

		Map<String, CFModDesdeJar.ArchivoLocalCF> archivosPorNombre = new HashMap<String, CFModDesdeJar.ArchivoLocalCF>();

		for (CFModDesdeJar.ArchivoLocalCF archivo : archivosLocales) {
			archivosPorNombre.put(nombreArchivo(archivo.rutaRelativa.toString()), archivo);
		}

		int agregados = 0;

		Json.Nodo version = raiz.obtener("modpack").obtener("version");

		agregados += completarIdsCurseForgeFaltantesEnArreglo(version.obtener("mods"), archivosPorNombre);
		agregados += completarIdsCurseForgeFaltantesEnArreglo(version.obtener("resourcePacks"), archivosPorNombre);
		agregados += completarIdsCurseForgeFaltantesEnArreglo(version.obtener("shaderPacks"), archivosPorNombre);
		agregados += completarIdsCurseForgeFaltantesEnArreglo(version.obtener("dataPacks"), archivosPorNombre);

		return agregados;
	}

	private static int completarIdsCurseForgeFaltantesEnArreglo(Json.Nodo arreglo,
			Map<String, CFModDesdeJar.ArchivoLocalCF> archivosPorNombre) {

		if (arreglo == null || !arreglo.esArreglo()) {
			return 0;
		}

		int agregados = 0;

		for (int i = 0; i < arreglo.tamano(); i++) {
			agregados += completarIdsCurseForgeFaltantesEnEntrada(arreglo.en(i), archivosPorNombre);
		}

		return agregados;
	}

	private static int completarIdsCurseForgeFaltantesEnEntrada(Json.Nodo entrada,
			Map<String, CFModDesdeJar.ArchivoLocalCF> archivosPorNombre) {

		try {
			long projectIdExistente = obtenerLargoSeguro(entrada.obtener("id"), 0L);
			long fileIdExistente = obtenerLargoSeguro(entrada.obtener("version").obtener("id"), 0L);

			boolean projectIdValido = projectIdExistente > 0L;
			boolean fileIdValido = fileIdExistente > 0L;

			// Solo se reconsulta CurseForge cuando falta o es inválido alguno de los IDs.
			// IDs negativos, cero o ausentes NO son válidos.
			if (projectIdValido && fileIdValido) {
				return 0;
			}

			String nombre = obtenerNombreArchivoEntrada(entrada);

			if (nombre == null || nombre.trim().isEmpty()) {
				return 0;
			}

			CFModDesdeJar.ArchivoLocalCF archivo = archivosPorNombre.get(nombre);

			if (archivo == null || archivo.archivo == null || !Files.isRegularFile(archivo.archivo)) {
				return 0;
			}

			if (archivo.fingerprint <= 0L) {
				archivo.fingerprint = CFModDesdeJar.calcularFingerprintCurseForge(archivo.archivo);
			}

			if (archivo.sha1 == null || archivo.sha1.trim().isEmpty()) {
				archivo.sha1 = CFModDesdeJar.calcularSha1(archivo.archivo);
			}

			if (archivo.tamano <= 0L) {
				archivo.tamano = Files.size(archivo.archivo);
			}

			List<CFModDesdeJar.ArchivoLocalCF> lista = new ArrayList<CFModDesdeJar.ArchivoLocalCF>();
			lista.add(archivo);

			Json.Nodo respuesta = CFModDesdeJar.solicitarCoincidenciasPorFingerprints(lista);
			Map<Long, Json.Nodo> coincidencias = indexarCoincidenciasPorFingerprint(respuesta);

			Json.Nodo match = coincidencias.get(Long.valueOf(archivo.fingerprint));

			if (match == null) {
				return 0;
			}

			Json.Nodo file = match.obtener("file");
			Json.Nodo mod = match.obtener("mod");

			long projectId = obtenerLargoSeguro(file.obtener("modId"), obtenerLargoSeguro(mod.obtener("id"), 0L));
			long fileId = obtenerLargoSeguro(file.obtener("id"), 0L);

			if (projectId <= 0L || fileId <= 0L) {
				return 0;
			}

			int agregados = 0;

			if (!projectIdValido) {
				entrada.obtener("id").poner(projectId);
				agregados++;
			}

			if (!fileIdValido) {
				entrada.obtener("version").obtener("id").poner(fileId);
				agregados++;
			}

			Json.Nodo metadata = entrada.obtener("version").obtener("metadata");

			if (obtenerCadenaSeguro(metadata.obtener("sha1"), "").trim().isEmpty()) {
				metadata.obtener("sha1").poner(archivo.sha1);
				agregados++;
			}

			if (obtenerLargoSeguro(metadata.obtener("size"), 0L) <= 0L) {
				metadata.obtener("size").poner(archivo.tamano);
				agregados++;
			}

			return agregados;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return 0;
		}
	}

	private static void asegurarEstructuraBasicaModpack(Json.Nodo raiz) {
		Json.Nodo modpack = raiz.obtener("modpack");

		if (obtenerCadenaSeguro(modpack.obtener("name"), "").trim().isEmpty()) {
			modpack.obtener("name").poner("Modpack");
		}

		Json.Nodo version = modpack.obtener("version");

		if (version.obtener("mods") == null || !version.obtener("mods").esArreglo()) {
			version.obtener("mods").poner(Json.leer("[]"));
		}

		if (version.obtener("resourcePacks") == null || !version.obtener("resourcePacks").esArreglo()) {
			version.obtener("resourcePacks").poner(Json.leer("[]"));
		}

		if (version.obtener("shaderPacks") == null || !version.obtener("shaderPacks").esArreglo()) {
			version.obtener("shaderPacks").poner(Json.leer("[]"));
		}

		if (version.obtener("dataPacks") == null || !version.obtener("dataPacks").esArreglo()) {
			version.obtener("dataPacks").poner(Json.leer("[]"));
		}
	}

	private static Json.Nodo leerJsonSeguro(Path archivoJson) throws IOException {
		String texto = new String(Files.readAllBytes(archivoJson), StandardCharsets.UTF_8);

		if (texto.trim().isEmpty()) {
			return crearJsonBase("Modpack");
		}

		return Json.leer(texto);
	}

	private static Json.Nodo crearJsonBase(String nombreModpack) {
		Json.Nodo raiz = Json.crearObjeto();

		raiz.obtener("activateSkinCapeForUserVersion").poner(false);
		raiz.obtener("skinVersion").poner(false);
		raiz.obtener("skipHashsumValidation").poner(false);
		raiz.obtener("additionalFiles").poner(Json.leer("[]"));
		raiz.obtener("tlauncherVersion").poner(0);

		Json.Nodo modpack = raiz.obtener("modpack");
		modpack.obtener("modpackMemory").poner(false);
		modpack.obtener("memory").poner(0);
		modpack.obtener("id").poner(System.currentTimeMillis() * -1L);
		modpack.obtener("name").poner(nombreModpack);
		modpack.obtener("favorite").poner(false);

		Json.Nodo version = modpack.obtener("version");
		version.obtener("loader").poner("");
		version.obtener("loaderId").poner("");
		version.obtener("loaderVersion").poner("");
		version.obtener("gameVersion").poner("");
		version.obtener("minecraftVersion").poner("");
		version.obtener("libraries").poner(Json.leer("[]"));

		version.obtener("mods").poner(Json.leer("[]"));
		version.obtener("resourcePacks").poner(Json.leer("[]"));
		version.obtener("shaderPacks").poner(Json.leer("[]"));
		version.obtener("dataPacks").poner(Json.leer("[]"));

		return raiz;
	}

	private static Map<CFModDesdeJar.TipoArchivoCF, Set<String>> crearIndiceNombresLocales(
			List<CFModDesdeJar.ArchivoLocalCF> archivosLocales) {

		Map<CFModDesdeJar.TipoArchivoCF, Set<String>> ret = new HashMap<CFModDesdeJar.TipoArchivoCF, Set<String>>();

		for (CFModDesdeJar.TipoArchivoCF tipo : CFModDesdeJar.TipoArchivoCF.values()) {
			ret.put(tipo, new HashSet<String>());
		}

		for (CFModDesdeJar.ArchivoLocalCF archivo : archivosLocales) {
			ret.get(archivo.tipo).add(nombreArchivo(archivo.rutaRelativa.toString()));
		}

		return ret;
	}

	private static int filtrarEntradasExistentes(Json.Nodo raiz, String nombreArreglo, CFModDesdeJar.TipoArchivoCF tipo,
			Map<CFModDesdeJar.TipoArchivoCF, Set<String>> nombresLocalesPorTipo,
			Map<CFModDesdeJar.TipoArchivoCF, Set<String>> nombresExistentesPorTipo) {

		Json.Nodo version = raiz.obtener("modpack").obtener("version");
		Json.Nodo arregloOriginal = version.obtener(nombreArreglo);

		Set<String> locales = nombresLocalesPorTipo.containsKey(tipo) ? nombresLocalesPorTipo.get(tipo)
				: Collections.<String>emptySet();

		Set<String> existentes = new HashSet<String>();
		nombresExistentesPorTipo.put(tipo, existentes);

		Json.Nodo nuevoArreglo = Json.leer("[]");
		int removidos = 0;

		if (arregloOriginal != null && arregloOriginal.esArreglo()) {
			for (int i = 0; i < arregloOriginal.tamano(); i++) {
				Json.Nodo entrada = arregloOriginal.en(i);
				String nombre = obtenerNombreArchivoEntrada(entrada);

				if (nombre == null || nombre.trim().isEmpty()) {
					nuevoArreglo.agregar(entrada);
					continue;
				}

				if (locales.contains(nombre)) {
					existentes.add(nombre);
					nuevoArreglo.agregar(entrada);
				} else {
					removidos++;
				}
			}
		}

		version.obtener(nombreArreglo).poner(nuevoArreglo);
		return removidos;
	}

	public static int completarIdsModrinthFaltantesCarpetaActual() throws IOException {
		return completarIdsModrinthFaltantes(Paths.get(".").toAbsolutePath().normalize());
	}

	public static int completarIdsModrinthFaltantes(Path carpetaInstancia) throws IOException {
		carpetaInstancia = carpetaInstancia.toAbsolutePath().normalize();

		Path archivoJson = carpetaInstancia.resolve(NOMBRE_ARCHIVO);

		if (!Files.isRegularFile(archivoJson)) {
			throw new IOException("No existe " + NOMBRE_ARCHIVO + " en " + carpetaInstancia);
		}

		Json.Nodo raiz = leerJsonSeguro(archivoJson);

		int agregados = completarIdsModrinthFaltantes(raiz);

		if (agregados > 0) {
			Files.write(archivoJson, Json.escribir(raiz).getBytes(StandardCharsets.UTF_8));
		}

		return agregados;
	}

	public static int completarIdsModrinthFaltantes(Json.Nodo raiz) {
		int agregados = 0;

		Json.Nodo version = raiz.obtener("modpack").obtener("version");

		agregados += completarIdsModrinthFaltantesEnArreglo(version.obtener("mods"));
		agregados += completarIdsModrinthFaltantesEnArreglo(version.obtener("resourcePacks"));
		agregados += completarIdsModrinthFaltantesEnArreglo(version.obtener("shaderPacks"));
		agregados += completarIdsModrinthFaltantesEnArreglo(version.obtener("dataPacks"));

		return agregados;
	}

	private static int completarIdsModrinthFaltantesEnArreglo(Json.Nodo arreglo) {
		if (arreglo == null || !arreglo.esArreglo()) {
			return 0;
		}

		int agregados = 0;

		for (int i = 0; i < arreglo.tamano(); i++) {
			agregados += completarIdsModrinthFaltantesEnEntrada(arreglo.en(i));
		}

		return agregados;
	}

	private static int completarIdsModrinthFaltantesEnEntrada(Json.Nodo entrada) {
		try {
			String projectIdExistente = obtenerCadenaSeguro(entrada.obtener("modrinthProjectId"), "");
			String versionIdExistente = obtenerCadenaSeguro(entrada.obtener("version").obtener("modrinthVersionId"),
					"");

			if (!projectIdExistente.trim().isEmpty() && !versionIdExistente.trim().isEmpty()) {
				return 0;
			}

			String sha1 = obtenerCadenaSeguro(entrada.obtener("version").obtener("metadata").obtener("sha1"), "");

			if (sha1 == null || sha1.trim().isEmpty()) {
				return 0;
			}

			Json.Nodo versionMR = MRModDesdeJar.solicitarVersionPorSha1ModrinthSeguro(sha1);

			if (versionMR == null) {
				return 0;
			}

			String projectId = obtenerCadenaSeguro(versionMR.obtener("project_id"), "");
			String versionId = obtenerCadenaSeguro(versionMR.obtener("id"), "");

			if (projectId == null || projectId.trim().isEmpty()) {
				return 0;
			}

			if (versionId == null || versionId.trim().isEmpty()) {
				return 0;
			}

			int agregados = 0;

			if (projectIdExistente.trim().isEmpty()) {
				entrada.obtener("modrinthProjectId").poner(projectId);
				agregados++;
			}

			if (versionIdExistente.trim().isEmpty()) {
				entrada.obtener("version").obtener("modrinthVersionId").poner(versionId);
				agregados++;
			}

			return agregados;
		} catch (Throwable t) {
			return 0;
		}
	}

	private static List<CFModDesdeJar.ArchivoLocalCF> obtenerArchivosNuevos(
			List<CFModDesdeJar.ArchivoLocalCF> archivosLocales,
			Map<CFModDesdeJar.TipoArchivoCF, Set<String>> nombresExistentesPorTipo) {

		List<CFModDesdeJar.ArchivoLocalCF> nuevos = new ArrayList<CFModDesdeJar.ArchivoLocalCF>();

		for (CFModDesdeJar.ArchivoLocalCF archivo : archivosLocales) {
			Set<String> existentes = nombresExistentesPorTipo.get(archivo.tipo);
			String nombre = nombreArchivo(archivo.rutaRelativa.toString());

			if (existentes != null && existentes.contains(nombre)) {
				continue;
			}

			nuevos.add(archivo);
		}

		return nuevos;
	}

	private static void prepararFingerprintsMulticore(List<CFModDesdeJar.ArchivoLocalCF> archivos) throws IOException {
		if (archivos == null || archivos.isEmpty()) {
			return;
		}

		int hilos = Math.max(1, Runtime.getRuntime().availableProcessors());
		ExecutorService executor = Executors.newFixedThreadPool(hilos);
		List<Future<?>> futuros = new ArrayList<Future<?>>();

		for (final CFModDesdeJar.ArchivoLocalCF archivo : archivos) {
			futuros.add(executor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						archivo.fingerprint = CFModDesdeJar.calcularFingerprintCurseForge(archivo.archivo);
						archivo.sha1 = CFModDesdeJar.calcularSha1(archivo.archivo);
						archivo.tamano = Files.size(archivo.archivo);
					} catch (Throwable t) {
						throw new RuntimeException(t);
					}
				}
			}));
		}

		executor.shutdown();

		for (Future<?> futuro : futuros) {
			try {
				futuro.get();
			} catch (Exception e) {
				throw new IOException("No se pudieron preparar fingerprints de CurseForge.", e);
			}
		}
	}

	private static CFModDesdeJar.ResultadoCF analizarArchivosYaPreparados(List<CFModDesdeJar.ArchivoLocalCF> archivos)
			throws IOException {

		CFModDesdeJar.ResultadoCF resultado = new CFModDesdeJar.ResultadoCF();

		Json.Nodo respuesta = CFModDesdeJar.solicitarCoincidenciasPorFingerprints(archivos);
		Map<Long, Json.Nodo> coincidencias = indexarCoincidenciasPorFingerprint(respuesta);

		for (CFModDesdeJar.ArchivoLocalCF archivo : archivos) {
			Json.Nodo coincidencia = coincidencias.get(Long.valueOf(archivo.fingerprint));

			if (coincidencia == null) {
				resultado.sinCoincidencia.add(archivo);
				continue;
			}

			Json.Nodo entrada = CFModDesdeJar.crearEntradaTLauncherDesdeCoincidencia(archivo, coincidencia);

			if (archivo.tipo == CFModDesdeJar.TipoArchivoCF.MOD) {
				resultado.mods.add(entrada);
			} else if (archivo.tipo == CFModDesdeJar.TipoArchivoCF.RESOURCE_PACK) {
				resultado.resourcePacks.add(entrada);
			} else if (archivo.tipo == CFModDesdeJar.TipoArchivoCF.SHADER_PACK) {
				resultado.shaderPacks.add(entrada);
			} else if (archivo.tipo == CFModDesdeJar.TipoArchivoCF.DATA_PACK) {
				resultado.dataPacks.add(entrada);
			}
		}

		return resultado;
	}

	private static Map<Long, Json.Nodo> indexarCoincidenciasPorFingerprint(Json.Nodo respuesta) {
		Map<Long, Json.Nodo> ret = new LinkedHashMap<Long, Json.Nodo>();
		Json.Nodo exactMatches = respuesta.obtener("data").obtener("exactMatches");

		if (exactMatches == null || !exactMatches.esArreglo()) {
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

	private static int agregarEntradasNuevas(Json.Nodo raiz, String nombreArreglo, List<Json.Nodo> nuevas) {
		if (nuevas == null || nuevas.isEmpty()) {
			return 0;
		}

		Json.Nodo arreglo = raiz.obtener("modpack").obtener("version").obtener(nombreArreglo);

		if (arreglo == null || !arreglo.esArreglo()) {
			arreglo = Json.leer("[]");
			raiz.obtener("modpack").obtener("version").obtener(nombreArreglo).poner(arreglo);
		}

		for (Json.Nodo nueva : nuevas) {
			arreglo.agregar(nueva);
		}

		return nuevas.size();
	}

	private static String obtenerNombreArchivoEntrada(Json.Nodo entrada) {
		try {
			String path = entrada.obtener("version").obtener("metadata").obtener("path").comoCadena();

			if (path == null || path.trim().isEmpty()) {
				return null;
			}

			return nombreArchivo(path);
		} catch (Throwable t) {
			return null;
		}
	}

	private static String nombreArchivo(String path) {
		if (path == null) {
			return "";
		}

		String limpio = path.replace('\\', '/');
		int idx = limpio.lastIndexOf('/');

		if (idx >= 0 && idx + 1 < limpio.length()) {
			return limpio.substring(idx + 1);
		}

		return limpio;
	}

	private static boolean estaDentroDeCarpetaVersionesTLauncher(Path carpetaActual) {
		Path normal = carpetaActual.toAbsolutePath().normalize();

		for (int i = 0; i < normal.getNameCount(); i++) {
			String parte = normal.getName(i).toString();

			if (!"versions".equalsIgnoreCase(parte)) {
				continue;
			}

			if (i <= 0 || i + 1 >= normal.getNameCount()) {
				continue;
			}

			String anterior = normal.getName(i - 1).toString();

			if ("minecraft".equalsIgnoreCase(anterior) || ".minecraft".equalsIgnoreCase(anterior)) {
				return true;
			}
		}

		return false;
	}

	private static long obtenerLargoSeguro(Json.Nodo nodo, long def) {
		try {
			return nodo.comoLargo();
		} catch (Throwable t) {
			return def;
		}
	}

	private static void enriquecerConIdsModrinthSiExisten(Json.Nodo raiz) {
		Json.Nodo version = raiz.obtener("modpack").obtener("version");

		enriquecerArregloConIdsModrinth(version.obtener("mods"));
		enriquecerArregloConIdsModrinth(version.obtener("resourcePacks"));
		enriquecerArregloConIdsModrinth(version.obtener("shaderPacks"));
		enriquecerArregloConIdsModrinth(version.obtener("dataPacks"));
	}

	private static void enriquecerArregloConIdsModrinth(Json.Nodo arreglo) {
		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);
			enriquecerEntradaConIdsModrinth(entrada);
		}
	}

	private static void enriquecerEntradaConIdsModrinth(Json.Nodo entrada) {
		try {
			String sha1 = obtenerCadenaSeguro(entrada.obtener("version").obtener("metadata").obtener("sha1"), "");

			if (sha1 == null || sha1.trim().isEmpty()) {
				return;
			}

			// Si ya tiene ambos IDs, no hacemos llamada de red.
			String projectIdExistente = obtenerCadenaSeguro(entrada.obtener("modrinthProjectId"), "");
			String versionIdExistente = obtenerCadenaSeguro(entrada.obtener("version").obtener("modrinthVersionId"),
					"");

			if (!projectIdExistente.isEmpty() && !versionIdExistente.isEmpty()) {
				return;
			}

			Json.Nodo versionMR = MRModDesdeJar.solicitarVersionPorSha1Modrinth(sha1);

			String projectId = obtenerCadenaSeguro(versionMR.obtener("project_id"), "");
			String versionId = obtenerCadenaSeguro(versionMR.obtener("id"), "");

			if (projectId == null || projectId.trim().isEmpty()) {
				return;
			}

			if (versionId == null || versionId.trim().isEmpty()) {
				return;
			}

			// Nivel proyecto/mod.
			entrada.obtener("modrinthProjectId").poner(projectId);

			// Nivel versión.
			entrada.obtener("version").obtener("modrinthVersionId").poner(versionId);

		} catch (Throwable t) {
			// No se agrega nada si Modrinth no tiene coincidencia.
			// Esto mantiene TLauncherAdditional limpio y compatible con TLMods.
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

	public static class ResultadoActualizacion {
		public final boolean omitido;
		public final int agregados;
		public final int removidos;
		public final int sinCoincidencia;
		public final Path archivo;

		public ResultadoActualizacion(boolean omitido, int agregados, int removidos, int sinCoincidencia,
				Path archivo) {
			this.omitido = omitido;
			this.agregados = agregados;
			this.removidos = removidos;
			this.sinCoincidencia = sinCoincidencia;
			this.archivo = archivo;
		}

		public static ResultadoActualizacion omitido() {
			return new ResultadoActualizacion(true, 0, 0, 0, null);
		}
	}
}