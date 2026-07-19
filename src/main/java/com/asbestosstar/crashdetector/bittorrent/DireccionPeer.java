package com.asbestosstar.crashdetector.bittorrent;

import java.net.InetSocketAddress;

/** Dirección de un peer encontrada por tracker, magnet o conexión entrante. */
public final class DireccionPeer {
	private final String host;
	private final int puerto;

	public DireccionPeer(String host, int puerto) {
		if (host == null || host.trim().isEmpty() || puerto <= 0 || puerto > 65535) {
			throw new IllegalArgumentException();
		}
		this.host = host.trim();
		this.puerto = puerto;
	}

	public String host() {
		return host;
	}

	public int puerto() {
		return puerto;
	}

	public InetSocketAddress socketAddress() {
		return new InetSocketAddress(host, puerto);
	}

	@Override
	public int hashCode() {
		return 31 * host.toLowerCase(java.util.Locale.ROOT).hashCode() + puerto;
	}

	@Override
	public boolean equals(Object objeto) {
		if (!(objeto instanceof DireccionPeer)) {
			return false;
		}
		DireccionPeer otro = (DireccionPeer) objeto;
		return puerto == otro.puerto && host.equalsIgnoreCase(otro.host);
	}

	@Override
	public String toString() {
		return host.indexOf(':') >= 0 ? "[" + host + "]:" + puerto : host + ":" + puerto;
	}
}
