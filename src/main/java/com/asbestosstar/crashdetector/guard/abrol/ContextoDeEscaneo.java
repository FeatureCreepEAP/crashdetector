package com.asbestosstar.crashdetector.guard.abrol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Resultado global del análisis pesado.
 *
 * Aquí vive toda la información cara de calcular, para que los escáneres no
 * tengan que repetir trabajo.
 */
public class ContextoDeEscaneo {

	/** Todos los mods y submods ya descubiertos. */
	public final List<ArchivoDeMod> mods = Collections.synchronizedList(new ArrayList<ArchivoDeMod>());

	/** Índice por mod. */
	public final Map<String, AnalisisDeMod> analisisPorUbicacion = new ConcurrentHashMap<String, AnalisisDeMod>();

	/** Índice global: clase -> mods que la contienen. */
	public final Map<String, List<ArchivoDeMod>> modsPorClase = new ConcurrentHashMap<String, List<ArchivoDeMod>>();

	/** Índice global: archivo -> mods que lo contienen. */
	public final Map<String, List<ArchivoDeMod>> modsPorArchivo = new ConcurrentHashMap<String, List<ArchivoDeMod>>();

	/** Índice global: constante texto -> ocurrencias. */
	public final Map<String, List<OcurrenciaConstante>> ocurrenciasDeConstantes = new ConcurrentHashMap<String, List<OcurrenciaConstante>>();

	/** Índice global: referencia a método -> ocurrencias. */
	public final Map<String, List<OcurrenciaReferenciaMetodo>> ocurrenciasDeReferenciasAMetodo = new ConcurrentHashMap<String, List<OcurrenciaReferenciaMetodo>>();

}