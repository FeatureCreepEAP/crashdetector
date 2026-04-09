package com.asbestosstar.crashdetector.guard.abrol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Información pesada calculada una sola vez para un mod.
 */
public class AnalisisDeMod {

	public String ubicacionMod;

	public final List<String> clases = new ArrayList<String>();
	public final List<String> archivos = new ArrayList<String>();

	/**
	 * Datos por clase.
	 */
	public final ConcurrentMap<String, AnalisisDeClase> clasesAnalizadas = new ConcurrentHashMap<String, AnalisisDeClase>();

}
