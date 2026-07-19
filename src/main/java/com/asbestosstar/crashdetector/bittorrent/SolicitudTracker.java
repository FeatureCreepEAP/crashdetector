package com.asbestosstar.crashdetector.bittorrent;

/** Datos comunes para announces HTTP y UDP. */
final class SolicitudTracker {
	final byte[] infoHash;
	final byte[] peerId;
	final int puerto;
	final long descargado;
	final long restante;
	final long subido;
	final EventoTracker evento;
	final int cantidadDeseada;

	SolicitudTracker(byte[] infoHash, byte[] peerId, int puerto, long descargado, long restante, long subido,
			EventoTracker evento, int cantidadDeseada) {
		this.infoHash = infoHash;
		this.peerId = peerId;
		this.puerto = puerto;
		this.descargado = descargado;
		this.restante = restante;
		this.subido = subido;
		this.evento = evento;
		this.cantidadDeseada = cantidadDeseada;
	}
}
