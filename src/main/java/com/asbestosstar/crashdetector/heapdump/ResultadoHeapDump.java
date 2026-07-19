package com.asbestosstar.crashdetector.heapdump;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Resultado de un análisis rápido HPROF.
 *
 * Los tamaños son superficiales y estimados. No son tamaños retenidos ni un
 * árbol de dominadores.
 */
public final class ResultadoHeapDump {

	private final File archivo;
	private final List<EstadisticaHeap> estadisticas;
	private final long bytesTotalesEstimados;
	private final long objetosTotales;
	private final int tamanoIdentificador;

	public ResultadoHeapDump(File archivo, List<EstadisticaHeap> estadisticas, long bytesTotalesEstimados,
			long objetosTotales, int tamanoIdentificador) {
		this.archivo = archivo;
		this.estadisticas = new ArrayList<EstadisticaHeap>(estadisticas);
		this.bytesTotalesEstimados = bytesTotalesEstimados;
		this.objetosTotales = objetosTotales;
		this.tamanoIdentificador = tamanoIdentificador;

		Collections.sort(this.estadisticas, new Comparator<EstadisticaHeap>() {
			@Override
			public int compare(EstadisticaHeap a, EstadisticaHeap b) {
				int porBytes = Long.compare(b.bytesEstimados(), a.bytesEstimados());
				return porBytes != 0 ? porBytes : a.clase().compareToIgnoreCase(b.clase());
			}
		});
	}

	public File archivo() {
		return archivo;
	}

	public List<EstadisticaHeap> estadisticas() {
		return Collections.unmodifiableList(estadisticas);
	}

	public long bytesTotalesEstimados() {
		return bytesTotalesEstimados;
	}

	public long objetosTotales() {
		return objetosTotales;
	}

	public int tamanoIdentificador() {
		return tamanoIdentificador;
	}
}
