package com.asbestosstar.crashdetector.bittorrent;

import java.util.BitSet;

/** Reserva una pieza completa por conexión para evitar descargas duplicadas. */
final class PlanificadorPiezas {
	private final BitSet completas;
	private final BitSet enProgreso;
	private final int cantidad;
	private int cursor;

	PlanificadorPiezas(BitSet completas, int cantidad) {
		this.completas = completas;
		this.cantidad = cantidad;
		this.enProgreso = new BitSet(cantidad);
	}

	synchronized int reservar(BitSet disponibles) {
		if (disponibles == null) {
			return -1;
		}
		for (int desplazamiento = 0; desplazamiento < cantidad; desplazamiento++) {
			int indice = (cursor + desplazamiento) % cantidad;
			if (disponibles.get(indice) && !completas.get(indice) && !enProgreso.get(indice)) {
				enProgreso.set(indice);
				cursor = (indice + 1) % Math.max(1, cantidad);
				return indice;
			}
		}
		return -1;
	}

	synchronized void liberar(int indice) {
		if (indice >= 0) {
			enProgreso.clear(indice);
		}
	}

	synchronized void completar(int indice) {
		if (indice >= 0) {
			completas.set(indice);
			enProgreso.clear(indice);
		}
	}
}
