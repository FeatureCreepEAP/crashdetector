package com.asbestosstar.crashdetector.dto.modpack.modrinth;

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
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.importar.ImportadorModpack;
import com.asbestosstar.crashdetector.dto.modpack.importar.ImportadorParalelo;
import com.asbestosstar.crashdetector.dto.modpack.importar.InfoEntradaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;
import com.asbestosstar.crashdetector.dto.modpack.tlmods.ActualizadorTLauncherAdditional;

public class ImportadorModpackModrinth implements ImportadorModpack {

	private static final String NOMBRE_INDEX = "modrinth.index.json";
	private static final String NOMBRE_TLAUNCHER_ADDITIONAL = "TLauncherAdditional.json";

	@Override
	public String obtenerNombreImportador() {
		return "Modrinth";
	}

	@Override
	public String obtenerExtensionModpack() {
		return "mrpack";
	}

	@Override
	public boolean puedeImportar(Path archivoModpack) {
		return archivoModpack != null && archivoModpack.getFileName() != null
				&& archivoModpack.getFileName().toString().toLowerCase().endsWith(".mrpack");
	}

	@Override
	public ResultadoImportacion importarModpack(Path archivoModpack, Path carpetaDestino, PoliticaImportacion politica)
			throws IOException {

		return importar(archivoModpack, carpetaDestino, politica, null);
	}

	public ResultadoImportacion importar(Path archivoModpack, Path carpetaDestino, PoliticaImportacion politica,
			ResolutorConflictosImportacion resolutor) throws IOException {

		if (archivoModpack == null || !Files.isRegularFile(archivoModpack)) {
			throw new IOException("El modpack Modrinth no existe: " + archivoModpack);
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
		Json.Nodo index = leerJsonDesdeZip(entradas, NOMBRE_INDEX);
		Json.Nodo tla = leerJsonDesdeZip(entradas, NOMBRE_TLAUNCHER_ADDITIONAL);

		if (index == null) {
			throw new IOException("El archivo .mrpack no contiene " + NOMBRE_INDEX + ".");
		}

		mezclar(total, instalarOverrides(entradas, carpetaDestino, politica));
		mezclar(total, descargarArchivosDesdeIndex(index, carpetaDestino, politica));

		if (tla != null) {
			Path archivoTLA = carpetaDestino.resolve(NOMBRE_TLAUNCHER_ADDITIONAL);
			InfoEntradaImportacion info = crearInfoSimple(NOMBRE_TLAUNCHER_ADDITIONAL);

			mezclar(total, instalarEntradaConConflictos(Json.escribir(tla).getBytes(StandardCharsets.UTF_8), info,
					archivoTLA, politica));
		}

		actualizarTLauncherAdditionalDespuesDeImportar(carpetaDestino);

		return total;
	}

	private ResultadoImportacion instalarOverrides(Map<String, EntradaZipLeida> entradas, Path carpetaDestino,
			PoliticaImportacion politica) throws IOException {

		final List<ImportadorParalelo.TrabajoImportacion> trabajos = ImportadorParalelo.listaSincronizada();

		for (final EntradaZipLeida entrada : entradas.values()) {
			String nombre = entrada.nombre.replace('\\', '/');

			if (!nombre.startsWith("overrides/")) {
				continue;
			}

			final String rutaRelativa = nombre.substring("overrides/".length());

			if (rutaRelativa.trim().isEmpty()) {
				continue;
			}

			if (!rutaSegura(rutaRelativa)) {
				final String nombreEntrada = nombre;

				trabajos.add(new ImportadorParalelo.TrabajoImportacion() {
					@Override
					public ResultadoImportacion ejecutar() {
						ResultadoImportacion r = new ResultadoImportacion();
						r.saltados++;
						r.mensajes.add("Ruta insegura omitida: " + nombreEntrada);
						return r;
					}
				});

				continue;
			}

			final Path destino = carpetaDestino.resolve(rutaRelativa).normalize();

			if (!destino.toAbsolutePath().normalize().startsWith(carpetaDestino)) {
				final String nombreEntrada = nombre;

				trabajos.add(new ImportadorParalelo.TrabajoImportacion() {
					@Override
					public ResultadoImportacion ejecutar() {
						ResultadoImportacion r = new ResultadoImportacion();
						r.saltados++;
						r.mensajes.add("Ruta intenta salir de la carpeta destino: " + nombreEntrada);
						return r;
					}
				});

				continue;
			}

			final String nombreEntrada = nombre;
			final PoliticaImportacion politicaFinal = politica;

			trabajos.add(new ImportadorParalelo.TrabajoImportacion() {
				@Override
				public ResultadoImportacion ejecutar() throws Exception {
					InfoEntradaImportacion info = crearInfoSimple(rutaRelativa);
					info.nombreEntrada = nombreEntrada;
					info.fechaModificacion = entrada.fechaModificacion;
					info.tamanoDeclarado = entrada.bytes.length;
					info.esMod = rutaRelativa.startsWith("mods/");

					return instalarEntradaConConflictos(entrada.bytes, info, destino, politicaFinal);
				}
			});
		}

		return ImportadorParalelo.ejecutar(trabajos);
	}

	private ResultadoImportacion descargarArchivosDesdeIndex(Json.Nodo index, Path carpetaDestino,
			PoliticaImportacion politica) throws IOException {

		Json.Nodo files = index.obtener("files");
		if (files == null || !files.esArreglo()) {
			return new ResultadoImportacion();
		}

		final List<ImportadorParalelo.TrabajoImportacion> trabajos = ImportadorParalelo.listaSincronizada();

		for (int i = 0; i < files.tamano(); i++) {
			final Json.Nodo file = files.en(i);

			final String path = obtenerCadenaSeguro(file.obtener("path"), "");
			final String sha1 = obtenerCadenaSeguro(file.obtener("hashes").obtener("sha1"), "");
			final String sha512 = obtenerCadenaSeguro(file.obtener("hashes").obtener("sha512"), "");
			final long fileSize = obtenerLargoSeguro(file.obtener("fileSize"), 0L);

			if (path.trim().isEmpty()) {
				trabajos.add(new ImportadorParalelo.TrabajoImportacion() {
					@Override
					public ResultadoImportacion ejecutar() {
						ResultadoImportacion r = new ResultadoImportacion();
						r.saltados++;
						r.mensajes.add("Archivo Modrinth sin path.");
						return r;
					}
				});
				continue;
			}

			if (!rutaSegura(path)) {
				trabajos.add(new ImportadorParalelo.TrabajoImportacion() {
					@Override
					public ResultadoImportacion ejecutar() {
						ResultadoImportacion r = new ResultadoImportacion();
						r.saltados++;
						r.mensajes.add("Ruta insegura omitida: " + path);
						return r;
					}
				});
				continue;
			}

			final Path destino = carpetaDestino.resolve(path).normalize();

			if (!destino.toAbsolutePath().normalize().startsWith(carpetaDestino)) {
				trabajos.add(new ImportadorParalelo.TrabajoImportacion() {
					@Override
					public ResultadoImportacion ejecutar() {
						ResultadoImportacion r = new ResultadoImportacion();
						r.saltados++;
						r.mensajes.add("Ruta intenta salir de la carpeta destino: " + path);
						return r;
					}
				});
				continue;
			}

			final PoliticaImportacion politicaFinal = politica;

			trabajos.add(new ImportadorParalelo.TrabajoImportacion() {
				@Override
				public ResultadoImportacion ejecutar() {
					ResultadoImportacion total = new ResultadoImportacion();

					try {
						String url = obtenerPrimeraDescarga(file);

						if (url.trim().isEmpty()) {
							total.saltados++;
							total.mensajes.add("Archivo Modrinth sin URL de descarga: " + path);
							return total;
						}

						byte[] bytes = descargarBytes(url);

						if (!sha1.trim().isEmpty()) {
							verificarSha1SiExiste(bytes, sha1, path);
						}

						if (!sha512.trim().isEmpty()) {
							verificarSha512SiExiste(bytes, sha512, path);
						}

						InfoEntradaImportacion info = crearInfoSimple(path);
						info.sha1 = sha1;
						info.tamanoDeclarado = fileSize > 0L ? fileSize : bytes.length;
						info.esMod = path.startsWith("mods/");

						try {
							if (!sha1.trim().isEmpty()) {
								Json.Nodo versionMR = MRModDesdeJar.solicitarVersionPorSha1Modrinth(sha1);
								info.modrinthProjectId = obtenerCadenaSeguro(versionMR.obtener("project_id"), "");
								info.modrinthVersionId = obtenerCadenaSeguro(versionMR.obtener("id"), "");
								info.fechaModificacion = parsearFecha(
										obtenerCadenaSeguro(versionMR.obtener("date_published"), ""));
							}
						} catch (Throwable t) {
							CrashDetectorLogger.logException(t);
						}

						return instalarEntradaConConflictos(bytes, info, destino, politicaFinal);
					} catch (Throwable t) {
						CrashDetectorLogger.logException(t);
						total.errores++;
						total.mensajes.add("No se pudo instalar archivo Modrinth " + path + ": " + t.getMessage());
						return total;
					}
				}
			});
		}

		return ImportadorParalelo.ejecutar(trabajos);
	}

	private static String obtenerPrimeraDescarga(Json.Nodo file) {
		try {
			Json.Nodo downloads = file.obtener("downloads");

			if (downloads != null && downloads.esArreglo() && downloads.tamano() > 0) {
				return obtenerCadenaSeguro(downloads.en(0), "");
			}
		} catch (Throwable t) {
			return "";
		}

		return "";
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

	private static InfoEntradaImportacion crearInfoSimple(String rutaRelativa) {
		InfoEntradaImportacion info = new InfoEntradaImportacion();
		info.rutaRelativa = rutaRelativa.replace('\\', '/');
		info.nombreEntrada = rutaRelativa;
		info.esMod = info.rutaRelativa.startsWith("mods/");
		return info;
	}

	private static void verificarSha1SiExiste(byte[] bytes, String esperado, String ruta) throws IOException {
		String real = hash(bytes, "SHA-1");

		if (!real.equalsIgnoreCase(esperado.trim())) {
			throw new IOException("SHA-1 no coincide para " + ruta + ". Esperado: " + esperado + ", real: " + real);
		}
	}

	private static void verificarSha512SiExiste(byte[] bytes, String esperado, String ruta) throws IOException {
		String real = hash(bytes, "SHA-512");

		if (!real.equalsIgnoreCase(esperado.trim())) {
			throw new IOException("SHA-512 no coincide para " + ruta + ". Esperado: " + esperado + ", real: " + real);
		}
	}

	private static String hash(byte[] bytes, String algoritmo) throws IOException {
		try {
			MessageDigest md = MessageDigest.getInstance(algoritmo);
			byte[] digest = md.digest(bytes);

			StringBuilder sb = new StringBuilder();

			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			return sb.toString();
		} catch (Throwable t) {
			throw new IOException("No se pudo calcular hash " + algoritmo + ".", t);
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

	private static void actualizarTLauncherAdditionalDespuesDeImportar(Path carpetaDestino) {
		try {
			ActualizadorTLauncherAdditional.actualizar(carpetaDestino);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private static void mezclar(ResultadoImportacion total, ResultadoImportacion r) {
		ImportadorModpack.mezclar(total, r);
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

	private static class EntradaZipLeida {
		String nombre;
		byte[] bytes;
		long fechaModificacion;
	}
}