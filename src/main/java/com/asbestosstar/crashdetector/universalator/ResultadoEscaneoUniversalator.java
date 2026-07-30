package com.asbestosstar.crashdetector.universalator;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Resultado de un mod que probablemente sea de lado cliente y convenga quitar
 * del pack del servidor.
 */
public class ResultadoEscaneoUniversalator implements Comparable<ResultadoEscaneoUniversalator> {

	public static final String RAZON_ENTORNO_CLIENTE = "entorno_cliente";
	public static final String RAZON_METADATOS_CLIENTE = "metadatos_cliente";
	public static final String RAZON_HEURISTICA_NOMBRE = "heuristica_nombre";

	private final Path archivo;
	private final String nombre;
	private final Set<String> razones = new LinkedHashSet<String>();
	private int confianza;

	public ResultadoEscaneoUniversalator(Path archivo, String nombre) {
		this.archivo = archivo;
		this.nombre = nombre;
	}

	public Path getArchivo() {
		return archivo;
	}

	public String getNombre() {
		return nombre;
	}

	public Set<String> getRazones() {
		return razones;
	}

	public int getConfianza() {
		return confianza;
	}

	public void incrementarConfianza(int puntos) {
		this.confianza += puntos;
		if (this.confianza > 100) {
			this.confianza = 100;
		}
	}

	public void agregarRazon(String razon) {
		if (razon != null && !razon.trim().isEmpty()) {
			razones.add(razon);
		}
	}

	public boolean tieneRazones() {
		return !razones.isEmpty();
	}

	@Override
	public int compareTo(ResultadoEscaneoUniversalator o) {
		if (o == null) {
			return -1;
		}
		int c = Integer.compare(o.confianza, confianza);
		if (c != 0) {
			return c;
		}
		return nombre.compareToIgnoreCase(o.nombre);
	}
}
