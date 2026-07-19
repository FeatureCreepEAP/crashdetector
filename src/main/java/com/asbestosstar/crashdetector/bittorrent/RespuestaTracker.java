package com.asbestosstar.crashdetector.bittorrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Respuesta normalizada de tracker. */
final class RespuestaTracker {
	private final int intervaloSegundos;
	private final int completos;
	private final int incompletos;
	private final List<DireccionPeer> pares;

	RespuestaTracker(int intervaloSegundos, int completos, int incompletos, List<DireccionPeer> pares) {
		this.intervaloSegundos = Math.max(60, intervaloSegundos);
		this.completos = completos;
		this.incompletos = incompletos;
		this.pares = Collections.unmodifiableList(new ArrayList<DireccionPeer>(pares));
	}

	int intervaloSegundos() {
		return intervaloSegundos;
	}

	int completos() {
		return completos;
	}

	int incompletos() {
		return incompletos;
	}

	List<DireccionPeer> pares() {
		return pares;
	}
}
