package com.asbestosstar.crashdetector.dto.modpack.modrinth;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.bbsmc.ProveedorModsBBSMC;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;

public class ProveedorModsModrinth extends ProveedorModsBBSMC {

	public static final String ENDPOINT = ConfigString.de("modrinth.endpoint", "https://api.modrinth.com/v2/search")
			.obtener();

	@Override
	public PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {
		return SolicitudBusquedaModsModrinth.buscarMods(ENDPOINT, idioma, pagina, termino);
	}

	@Override
	public InternetMod obtenerModPorId(long identificador, String idioma) throws IOException {
		throw new UnsupportedOperationException("No implementado aún para Modrinth");
	}

	@Override
	public String obtenerNombreProveedor() {
		return "Modrinth";
	}

	@Override
	public boolean soportaImportarModpack() {
		return true;
	}

	@Override
	public ResultadoImportacion importarModpack(Path ubicacionArchivoModpack, Path carpetaDestino,
			PoliticaImportacion politica, ResolutorConflictosImportacion resolutor) throws IOException {

		ImportadorModpackModrinth importador = new ImportadorModpackModrinth();
		return importador.importar(ubicacionArchivoModpack, carpetaDestino, politica, resolutor);
	}

	@Override
	public boolean soportaExportarModpack() {
		return true;
	}

	@Override
	public void exportarModpack(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		ExportadorModpackModrinth.exportar(ubicacionArchivoModpack, rutasEntrada);
	}

}