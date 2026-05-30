package com.asbestosstar.crashdetector.buscar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

public class ProcesadorManifiesto {

	/**
	 * Procesa un InputStream de un archivo MANIFEST.MF para extraer los nombres de
	 * módulo. Busca el atributo "Automatic-Module-Name" en la sección principal del
	 * manifiesto.
	 *
	 * @param flujoManifiesto El InputStream que apunta al archivo MANIFEST.MF.
	 * @return Una List<String> con los nombres de módulo encontrados. Si el
	 *         atributo no se encuentra, devuelve una lista vacía.
	 * @throws IOException Si ocurre un error de entrada/salida al leer el flujo.
	 */
	public static List<String> obtenerNombresDeModulo(Manifest manifiesto) throws IOException {
		List<String> nombresModulos = new ArrayList<>();

		// Obtener los atributos principales del manifiesto
		Attributes atributosPrincipales = manifiesto.getMainAttributes();

		// Buscar el atributo que contiene el nombre del módulo
		String valorNombreModulo = atributosPrincipales.getValue("Automatic-Module-Name");

		if (valorNombreModulo != null && !valorNombreModulo.trim().isEmpty()) {
			// El valor puede contener múltiples nombres separados por comas
			String[] nombres = valorNombreModulo.split(",");
			for (String nombre : nombres) {
				// Añadir cada nombre, eliminando espacios en blanco
				String nombreRecortado = nombre.trim();
				if (!nombreRecortado.isEmpty()) {
					nombresModulos.add(nombreRecortado);
				}
			}
		}

		return nombresModulos;
	}
}