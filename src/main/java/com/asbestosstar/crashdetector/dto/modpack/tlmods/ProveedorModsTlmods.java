package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;

public class ProveedorModsTlmods implements ProveedorMods {

	public static ConfigString ENDPOINT = ConfigString.de("tlmods.endpoint", "https://tlmods.org/");

	@Override
	public PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {
		return SolicitudBusquedaModsTlmods.buscarMods(idioma, pagina, termino);
	}

	@Override
	public InternetMod obtenerModPorId(long identificador, String idioma) throws IOException {
		throw new UnsupportedOperationException("No implementado aún");
	}

	@Override
	public String obtenerNombreProveedor() {
		return "TLauncher MPS (tlmods)";
	}

	@Override
	public boolean soportaBusqueda() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean soportaImportarModpack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultadoImportacion importarModpack(Path ubicacionArchivoModpack, Path carpetaDestino,
			PoliticaImportacion politica, ResolutorConflictosImportacion resolutor) throws IOException {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean soportaExportarModpack() {
		return true;
	}

//	@Override
//	public String obtenerExtensionModpack() {
//		return "zip";
//	}

	@Override
	public void exportarModpack(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		ExportadorModpackTlmods.exportar(ubicacionArchivoModpack, rutasEntrada);
	}

}