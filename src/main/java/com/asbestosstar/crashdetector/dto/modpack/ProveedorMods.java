package com.asbestosstar.crashdetector.dto.modpack;

import java.io.IOException;
import java.util.List;

/**
 * Contrato para cualquier fuente de mods (TLMods, CurseForge, etc.). Permite
 * abstraer la lógica de obtención de información de mods.
 */
public interface ProveedorMods {

	/**
	 * Busca mods por nombre o descripción.
	 * 
	 * @param idioma  Código de idioma (ej. "ES", "EN")
	 * @param pagina  Número de página (comienza en 0)
	 * @param termino Término de búsqueda
	 * @return Página de resultados
	 * @throws IOException si falla la comunicación
	 */
	PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException;

	/**
	 * Obtiene detalles completos de un mod por su identificador único.
	 * 
	 * @param identificador ID del mod
	 * @param idioma        Código de idioma
	 * @return Información completa del mod
	 * @throws IOException si no se encuentra o falla la red
	 */
	InternetMod obtenerModPorId(long identificador, String idioma) throws IOException;

	/**
	 * Nombre del proveedor (para logs y UI).
	 */
	String obtenerNombreProveedor();
}