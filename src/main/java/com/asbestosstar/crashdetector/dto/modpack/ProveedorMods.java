package com.asbestosstar.crashdetector.dto.modpack;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;

/**
 * Contrato para cualquier fuente de mods (TLMods, CurseForge, etc.). Permite
 * abstraer la lógica de obtención de información de mods.
 */
public interface ProveedorMods {

	public boolean soportaBusqueda();

	/**
	 * Busca mods por nombre o descripción.
	 * 
	 * @param idioma  Código de idioma (ej. "ES", "EN")
	 * @param pagina  Número de página (comienza en 0)
	 * @param termino Término de búsqueda
	 * @return Página de resultados
	 * @throws IOException si falla la comunicación
	 */
	public PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException;

	/**
	 * Obtiene detalles completos de un mod por su identificador único.
	 * 
	 * @param identificador ID del mod
	 * @param idioma        Código de idioma
	 * @return Información completa del mod
	 * @throws IOException si no se encuentra o falla la red
	 */
	public InternetMod obtenerModPorId(long identificador, String idioma) throws IOException;

	/**
	 * Nombre del proveedor (para logs y UI).
	 */
	public String obtenerNombreProveedor();

	/**
	 * Indica si este proveedor permite importar modpacks.
	 */
	public boolean soportaImportarModpack();

	/**
	 * Importa un modpack desde uno o más archivos de entrada.
	 * 
	 * @param ubicacionArchivoModpack ubicación del archivo principal del modpack
	 * @param rutasEntrada            rutas de archivos o carpetas necesarias para
	 *                                importar
	 * @throws IOException si falla la importación
	 */
	public ResultadoImportacion importarModpack(Path ubicacionArchivoModpack, Path carpetaDestino,
			PoliticaImportacion politica, ResolutorConflictosImportacion resolutor) throws IOException;

	/**
	 * Indica si este proveedor permite exportar modpacks.
	 */
	public boolean soportaExportarModpack();

	/**
	 * Exporta un modpack usando uno o más archivos/carpetas de entrada.
	 * 
	 * @param ubicacionArchivoModpack ubicación donde se guardará el archivo
	 *                                exportado
	 * @param rutasEntrada            rutas de archivos o carpetas que se incluirán
	 *                                en la exportación
	 * @throws IOException si falla la exportación
	 */
	public void exportarModpack(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException;

}