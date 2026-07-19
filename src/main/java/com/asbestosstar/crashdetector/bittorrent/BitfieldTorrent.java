package com.asbestosstar.crashdetector.bittorrent;

import java.util.BitSet;

/**
 * Conversión entre BitSet Java y bitfield de red, con bit más significativo
 * primero.
 */
final class BitfieldTorrent {
	private BitfieldTorrent() {
	}

	static byte[] codificar(BitSet piezas, int cantidad) {
		byte[] datos = new byte[(cantidad + 7) / 8];
		for (int i = piezas.nextSetBit(0); i >= 0 && i < cantidad; i = piezas.nextSetBit(i + 1)) {
			datos[i / 8] |= (byte) (0x80 >>> (i % 8));
		}
		return datos;
	}

	static BitSet decodificar(byte[] datos, int cantidad) {
		BitSet piezas = new BitSet(cantidad);
		if (datos == null) {
			return piezas;
		}
		int limite = Math.min(cantidad, datos.length * 8);
		for (int i = 0; i < limite; i++) {
			if ((datos[i / 8] & (0x80 >>> (i % 8))) != 0) {
				piezas.set(i);
			}
		}
		return piezas;
	}
}
