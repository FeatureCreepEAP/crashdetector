package com.asbestosstar.crashdetector.bittorrent;

import java.net.URI;
import java.util.Locale;

/** Selecciona el transporte de tracker según el esquema de su URI. */
final class FabricaTrackers {
	private FabricaTrackers() {
	}

	static ClienteTracker crear(String tracker) throws Exception {
		URI uri = new URI(tracker);
		String esquema = uri.getScheme() == null ? "" : uri.getScheme().toLowerCase(Locale.ROOT);
		if ("http".equals(esquema) || "https".equals(esquema)) {
			return new ClienteTrackerHttp(uri);
		}
		if ("udp".equals(esquema)) {
			return new ClienteTrackerUdp(uri);
		}
		throw new IllegalArgumentException();
	}
}
