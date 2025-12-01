package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import java.io.IOException;

public class ProveedorModsTlmods implements ProveedorMods {

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
}