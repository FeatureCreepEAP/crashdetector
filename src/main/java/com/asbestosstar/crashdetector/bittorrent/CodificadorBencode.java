package com.asbestosstar.crashdetector.bittorrent;

/**
 * Compatibilidad con el nombre usado por el creador de torrents anterior.
 */
final class CodificadorBencode {
	private CodificadorBencode() {
	}

	static byte[] codificar(Object valor) {
		return Bencode.codificar(valor);
	}
}
