package com.asbestosstar.crashdetector.dto.modpack.basico;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorConExtensionModpack;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.dto.modpack.importar.InfoEntradaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;

public class ProveedorModsBasico implements ProveedorMods, ProveedorConExtensionModpack {

	@Override
	public boolean soportaBusqueda() {
		return false;
	}

	@Override
	public PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {
		return null;
	}

	@Override
	public InternetMod obtenerModPorId(long identificador, String idioma) throws IOException {
		return null;
	}

	@Override
	public String obtenerNombreProveedor() {
		return "basico";
	}

	@Override
	public boolean soportaImportarModpack() {
		return true;
	}

	@Override
	public ResultadoImportacion importarModpack(Path ubicacionArchivoModpack, Path carpetaDestino,
			PoliticaImportacion politica, ResolutorConflictosImportacion resolutor) throws IOException {

		if (ubicacionArchivoModpack == null) {
			throw new IOException("La ubicacion del modpack basico es nula.");
		}

		if (!Files.isRegularFile(ubicacionArchivoModpack)) {
			throw new IOException("El modpack basico no existe: " + ubicacionArchivoModpack);
		}

		if (carpetaDestino == null) {
			carpetaDestino = java.nio.file.Paths.get(".").toAbsolutePath().normalize();
		}

		carpetaDestino = carpetaDestino.toAbsolutePath().normalize();

		if (politica == null) {
			politica = new PoliticaImportacion();
		}

		ResultadoImportacion total = new ResultadoImportacion();

		try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(ubicacionArchivoModpack))) {
			ZipEntry entrada;

			while ((entrada = zis.getNextEntry()) != null) {
				if (entrada.isDirectory()) {
					zis.closeEntry();
					continue;
				}

				String rutaRelativa = normalizarRutaEntrada(entrada.getName());

				if (rutaRelativa == null || rutaRelativa.trim().isEmpty()) {
					zis.closeEntry();
					continue;
				}

				if (!rutaPermitidaParaImportar(rutaRelativa)) {
					total.saltados++;
					total.mensajes.add("Ruta no permitida en modpack basico: " + entrada.getName());
					zis.closeEntry();
					continue;
				}

				byte[] bytes = leerBytes(zis);

				InfoEntradaImportacion info = new InfoEntradaImportacion();
				info.rutaRelativa = rutaRelativa;
				info.nombreEntrada = entrada.getName();
				info.fechaModificacion = entrada.getTime();
				info.tamanoDeclarado = entrada.getSize();
				info.esMod = esRutaMod(rutaRelativa);

				Path destino = carpetaDestino.resolve(rutaRelativa).normalize();

				if (!destino.toAbsolutePath().normalize().startsWith(carpetaDestino)) {
					total.saltados++;
					total.mensajes.add("Ruta intenta salir de la carpeta destino: " + entrada.getName());
					zis.closeEntry();
					continue;
				}

				ResultadoImportacion r = instalarEntradaConConflictos(bytes, info, destino, politica, resolutor);
				mezclarResultado(total, r);

				zis.closeEntry();
			}
		}

		return total;
	}

	protected ResultadoImportacion instalarEntradaConConflictos(byte[] bytesEntrada, InfoEntradaImportacion info,
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

		com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion conflicto = new com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion();

		conflicto.tipo = info != null && info.esMod
				? com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion.Tipo.MOD
				: com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion.Tipo.ARCHIVO;

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

		com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion.Decision decision;

		if (politica.reemplazarTodo) {
			decision = com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion.Decision.REEMPLAZAR;
		} else if (politica.saltarTodo) {
			decision = com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion.Decision.SALTAR;
		} else if (politica.renombrarTodo) {
			decision = com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion.Decision.RENOMBRAR;
		} else if (politica.noPreguntarSiImportadoEsMasNuevo && conflicto.importadoPareceMasNuevo) {
			decision = com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion.Decision.REEMPLAZAR;
		} else if (resolutor != null) {
			decision = resolutor.resolverConflicto(conflicto, politica);
		} else {
			decision = com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion.Decision.SALTAR;
		}

		if (decision == com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion.Decision.REEMPLAZAR) {
			Files.write(destino, bytesEntrada, java.nio.file.StandardOpenOption.CREATE,
					java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
			resultado.reemplazados++;
			return resultado;
		}

		if (decision == com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion.Decision.RENOMBRAR) {
			Path renombrado = crearRutaRenombrada(destino);
			Files.write(renombrado, bytesEntrada, java.nio.file.StandardOpenOption.CREATE_NEW);
			resultado.renombrados++;
			return resultado;
		}

		resultado.saltados++;
		return resultado;
	}

	protected Path crearRutaRenombrada(Path destino) {
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

	@Override
	public boolean soportaExportarModpack() {
		return true;
	}

	@Override
	public String obtenerExtensionModpack() {
		return "zip";
	}

	@Override
	public void exportarModpack(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		if (ubicacionArchivoModpack == null) {
			throw new IOException("La ubicacion del archivo de modpack es nula.");
		}

		if (rutasEntrada == null || rutasEntrada.isEmpty()) {
			throw new IOException("No hay rutas para exportar.");
		}

		Path padre = ubicacionArchivoModpack.getParent();
		if (padre != null) {
			Files.createDirectories(padre);
		}

		Path carpetaBase = java.nio.file.Paths.get(".").toAbsolutePath().normalize();
		Path carpetaStatics = Statics.carpeta.toAbsolutePath().normalize();

		try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(ubicacionArchivoModpack))) {
			Set<String> yaAgregadas = new LinkedHashSet<String>();

			for (Path ruta : rutasEntrada) {
				if (ruta == null || !Files.exists(ruta)) {
					continue;
				}

				if (Files.isRegularFile(ruta)) {
					agregarArchivoAlZip(zos, ruta, carpetaBase, carpetaStatics, yaAgregadas);
				} else if (Files.isDirectory(ruta)) {
					try (java.util.stream.Stream<Path> stream = Files.walk(ruta)) {
						stream.filter(Files::isRegularFile).forEach(p -> {
							try {
								agregarArchivoAlZip(zos, p, carpetaBase, carpetaStatics, yaAgregadas);
							} catch (Throwable t) {
								CrashDetectorLogger.logException(t);
							}
						});
					}
				}
			}
		}
	}

	private void agregarArchivoAlZip(ZipOutputStream zos, Path archivo, Path carpetaBase, Path carpetaStatics,
			Set<String> yaAgregadas) throws IOException {

		Path normal = archivo.toAbsolutePath().normalize();
		String nombreRelativo;

		if (normal.startsWith(carpetaBase)) {
			nombreRelativo = carpetaBase.relativize(normal).toString().replace('\\', '/');
		} else if (normal.startsWith(carpetaStatics)) {
			nombreRelativo = "_crashdetector/" + carpetaStatics.relativize(normal).toString().replace('\\', '/');
		} else {
			return;
		}

		if (!yaAgregadas.add(nombreRelativo)) {
			return;
		}

		zos.putNextEntry(new ZipEntry(nombreRelativo));
		Files.copy(archivo, zos);
		zos.closeEntry();
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

	private static void mezclarResultado(ResultadoImportacion total, ResultadoImportacion r) {
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

	private static boolean esRutaMod(String rutaRelativa) {
		if (rutaRelativa == null) {
			return false;
		}

		String r = rutaRelativa.replace('\\', '/').toLowerCase();
		return r.startsWith("mods/") && r.endsWith(".jar");
	}

	private static String normalizarRutaEntrada(String ruta) {
		if (ruta == null) {
			return null;
		}

		String r = ruta.replace('\\', '/');

		while (r.startsWith("/")) {
			r = r.substring(1);
		}

		if (r.contains("../") || r.startsWith("..")) {
			return null;
		}

		return r;
	}

	private static boolean rutaPermitidaParaImportar(String rutaRelativa) {
		return true;
	}
}