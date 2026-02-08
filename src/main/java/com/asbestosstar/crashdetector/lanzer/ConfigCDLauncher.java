package com.asbestosstar.crashdetector.lanzer;

import java.util.HashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.lanzer.servicio.CDProfiler;
import com.asbestosstar.crashdetector.lanzer.servicio.CDSampler;
import com.asbestosstar.crashdetector.lanzer.servicio.CDTracer;

/**
 * Configuración simple de opciones de CDLauncher. Cada entrada genera un
 * checkbox en la notificación.
 */
public class ConfigCDLauncher {

	/**
	 * Opciones de CDLauncher (clave -> habilitado).
	 */
	public static Map<String, Boolean> opciones = new HashMap<>();

	static {
		opciones.put(CDTracer.ID, false);
		opciones.put(CDProfiler.ID, false);
		opciones.put(CDSampler.ID, false);
	}
}
