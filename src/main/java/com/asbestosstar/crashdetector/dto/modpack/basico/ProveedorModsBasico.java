package com.asbestosstar.crashdetector.dto.modpack.basico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorConExtensionModpack;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;

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
		return false;
	}

	@Override
	public void importarModpack(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		throw new IOException("El proveedor basico no soporta importar modpacks.");
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
}