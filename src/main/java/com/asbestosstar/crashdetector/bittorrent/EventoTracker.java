package com.asbestosstar.crashdetector.bittorrent;

/** Evento de announce comunicado al tracker. */
enum EventoTracker {
	NINGUNO(0, ""), COMPLETADO(1, "completed"), INICIADO(2, "started"), DETENIDO(3, "stopped");

	private final int codigoUdp;
	private final String textoHttp;

	EventoTracker(int codigoUdp, String textoHttp) {
		this.codigoUdp = codigoUdp;
		this.textoHttp = textoHttp;
	}

	int codigoUdp() {
		return codigoUdp;
	}

	String textoHttp() {
		return textoHttp;
	}
}
