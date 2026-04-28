package com.asbestosstar.crashdetector.dto.modpack.curseforge;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;

public class ProveedorModsCurseForge implements ProveedorMods {

	private static final String claveApi = obtenerClaveApi();

	public static final String ENDPOINT = ConfigString.de("curseforge.endpoint", obtenerEndpointPredeterminada())
			.obtener();

	@Override
	public PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {
		return SolicitudBusquedaModsCurseForge.buscarMods(claveApi, idioma, pagina, termino);
	}

	@Override
	public InternetMod obtenerModPorId(long identificador, String idioma) throws IOException {
		throw new UnsupportedOperationException("No implementado aún");
	}

	@Override
	public String obtenerNombreProveedor() {
		return "CurseForge";
	}

	public static String obtenerClaveApi() {
		String claveCorperata = ConfigString.de("curseforge.api_key", "").obtener();
		String claveMundial = ConfigMundial.obtenerInstancia().obtenerCurseForgeClaveApi();
		String claveEnv = System.getenv("CURSEFORGE_API_KEY");
		if (claveCorperata != null && !claveCorperata.trim().isEmpty()) {
			return claveCorperata.trim();
		} else if (claveMundial != null && !claveMundial.trim().isEmpty()) {
			return claveMundial.trim();
		} else if (claveEnv != null && !claveEnv.trim().isEmpty()) {
			return claveEnv.trim();
		} else {
			return "usar_klauncher_endpoint_si_no_tienes_clave_api";
		}

	}

	public static String obtenerEndpointPredeterminada() {
		// TODO Auto-generated method stub

		if (obtenerClaveApi().equals("usar_klauncher_endpoint_si_no_tienes_clave_api") || obtenerClaveApi() == null
				|| obtenerClaveApi().trim().isEmpty()) {
			return "https://cf.klaun.ch/";
		} // Si tienes una clave API, puedes usar pero si no, puedes usar el endpoint de
			// KLauncher no comcerial

		return "https://api.curseforge.com/";
	}

	@Override
	public boolean soportaBusqueda() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean soportaImportarModpack() {
		return true;
	}

	@Override
	public ResultadoImportacion importarModpack(Path ubicacionArchivoModpack, Path carpetaDestino,
			PoliticaImportacion politica, ResolutorConflictosImportacion resolutor) throws IOException {

		ImportadorModpackCurseForge importador = new ImportadorModpackCurseForge();
		return importador.importar(ubicacionArchivoModpack, carpetaDestino, politica, resolutor);
	}

	@Override
	public boolean soportaExportarModpack() {
		return true;
	}

	@Override
	public void exportarModpack(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		ExportadorModpackCurseForge.exportar(ubicacionArchivoModpack, rutasEntrada);
	}

}