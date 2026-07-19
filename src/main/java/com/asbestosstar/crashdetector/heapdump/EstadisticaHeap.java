package com.asbestosstar.crashdetector.heapdump;

import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Estadística agregada de una clase o tipo de arreglo del heap.
 */
public final class EstadisticaHeap {

	private final String clase;
	private long instancias;
	private long bytesEstimados;
	private String mod;

	public EstadisticaHeap(String clase) {
		this.clase = clase == null || clase.trim().isEmpty() ? MonitorDePID.idioma.heapVisorClaseDesconocida() : clase;
	}

	public void agregar(long cantidad, long bytes) {
		if (cantidad > 0L) {
			instancias += cantidad;
		}
		if (bytes > 0L) {
			bytesEstimados += bytes;
		}
	}

	public String clase() {
		return clase;
	}

	public long instancias() {
		return instancias;
	}

	public long bytesEstimados() {
		return bytesEstimados;
	}

	public String mod() {
		return mod == null ? "" : mod;
	}

	public void establecerMod(String mod) {
		this.mod = mod == null ? "" : mod;
	}
}
