package com.asbestosstar.crashdetector.dto.modpack.bbsmc;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;

public class ProveedorModsBBSMC implements ProveedorMods {

	public static final String ENDPOINT = ConfigString.de("bbsmc.endpoint", "https://api.bbsmc.net/v2/search")
			.obtener();

	@Override
	public boolean soportaBusqueda() {
		return true;
	}

	@Override
	public PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {
		return SolicitudBusquedaModsBBSMC.buscarMods(ENDPOINT, idioma, pagina, termino);
	}

	@Override
	public InternetMod obtenerModPorId(long identificador, String idioma) throws IOException {
		throw new UnsupportedOperationException("No implementado aún para BBSMC");
	}

	@Override
	public String obtenerNombreProveedor() {
		return "BBSMC";
	}

	@Override
	public boolean soportaImportarModpack() {
		return false;
	}

	@Override
	public void importarModpack(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		throw new UnsupportedOperationException("BBSMC no soporta importar modpacks aún");
	}

	@Override
	public boolean soportaExportarModpack() {
		return false;
	}

	@Override
	public void exportarModpack(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		throw new UnsupportedOperationException("BBSMC no soporta exportar modpacks aún");
	}
}