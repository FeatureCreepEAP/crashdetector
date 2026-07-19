package com.asbestosstar.crashdetector.bittorrent;

/** Cliente de un tracker concreto. */
interface ClienteTracker {
	RespuestaTracker anunciar(SolicitudTracker solicitud) throws Exception;
}
