package com.asbestosstar.crashdetector.dto.modpack.packwiz;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;

public class ProveedorModsPackwiz implements ProveedorMods {

	@Override
	public boolean soportaBusqueda() {
		return false;
	}

	@Override
	public PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {
		throw new UnsupportedOperationException("Packwiz no es una fuente de búsqueda.");
	}

	@Override
	public InternetMod obtenerModPorId(long identificador, String idioma) throws IOException {
		throw new UnsupportedOperationException("Packwiz no obtiene mods por ID.");
	}

	@Override
	public String obtenerNombreProveedor() {
		return "Packwiz";
	}

	@Override
	public boolean soportaImportarModpack() {
		return true;
	}

	@Override
	public ResultadoImportacion importarModpack(Path ubicacionArchivoModpack, Path carpetaDestino,
			PoliticaImportacion politica, ResolutorConflictosImportacion resolutor) throws IOException {

		ImportadorModpackPackwiz importador = new ImportadorModpackPackwiz();
		return importador.importar(ubicacionArchivoModpack, carpetaDestino, politica, resolutor);
	}

	@Override
	public boolean soportaExportarModpack() {
		return true;
	}

	@Override
	public void exportarModpack(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		ExportadorModpackPackwiz.exportar(ubicacionArchivoModpack, rutasEntrada);
	}
}