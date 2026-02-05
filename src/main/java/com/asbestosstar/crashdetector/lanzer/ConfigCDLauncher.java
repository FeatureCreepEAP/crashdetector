package com.asbestosstar.crashdetector.lanzer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuración simple de opciones de CDLauncher.
 * Cada entrada genera un checkbox en la notificación.
 */
public class ConfigCDLauncher {

	/**
	 * Opciones de CDLauncher (clave -> habilitado).
	 */
	public static Map<String, Boolean> opciones = new HashMap<>();

	static {
		opciones.put("cdtracer", false);
	}
}
