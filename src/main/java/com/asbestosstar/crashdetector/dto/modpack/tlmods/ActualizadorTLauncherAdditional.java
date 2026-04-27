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
import com.asbestosstar.crashdetector.dto.modpack.curseforge.CFModDesdeJar;
import com.asbestosstar.crashdetector.dto.modpack.modrinth.MRModDesdeJar;
import com.asbestosstar.crashdetector.json.Json;

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

		if (estaDentroDeCarpetaVersionesTLauncher(carpetaInstancia)) {
			CrashDetectorLogger.log(
					"No se modifica TLauncherAdditional.json porque esta carpeta parece ser una instancia de TLauncher: "
							+ carpetaInstancia);
			return ResultadoActualizacion.omitido();
		}

		Path archivoJson = carpetaInstancia.resolve(NOMBRE_ARCHIVO);

		Json.Nodo raiz = Files.exists(archivoJson) ? leerJsonSeguro(archivoJson)
				: crearJsonBase(
						carpetaInstancia.getFileName() != null ? carpetaInstancia.getFileName().toString() : "Modpack");

		// Completa metadata principal: loader, loaderVersion, gameVersion,
		// libraries, additionalFiles y desactiva skin system.
		GeneradorManifiestoTLauncherAdditional.completarManifiestoPrincipal(raiz, carpetaInstancia);

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

		// Opcional: si Modrinth reconoce el SHA-1 del archivo, agrega:
		// entrada.modrinthProjectId
		// entrada.version.modrinthVersionId
		// Si no hay coincidencia, no agrega campos vacíos.
		// enriquecerConIdsModrinthSiExisten(raiz);

		Files.write(archivoJson, Json.escribir(raiz).getBytes(StandardCharsets.UTF_8));

		return new ResultadoActualizacion(false, agregados, removidos, resultadoCF.sinCoincidencia.size(), archivoJson);
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

			Json.Nodo versionMR = MRModDesdeJar.solicitarVersionPorSha1Modrinth(sha1);

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