package com.asbestosstar.crashdetector.bittorrent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/** Handshake fijo del peer-wire protocol BitTorrent v1. */
final class HandshakeBitTorrent {

	private static final byte[] PROTOCOLO = "BitTorrent protocol".getBytes(StandardCharsets.US_ASCII);
	private final byte[] reservados;
	private final byte[] infoHash;
	private final byte[] peerId;

	HandshakeBitTorrent(byte[] reservados, byte[] infoHash, byte[] peerId) {
		this.reservados = reservados;
		this.infoHash = infoHash;
		this.peerId = peerId;
	}

	static HandshakeBitTorrent leer(DataInputStream entrada) throws IOException {
		int largo = entrada.readUnsignedByte();
		if (largo != PROTOCOLO.length) {
			throw new IOException();
		}
		byte[] protocolo = new byte[largo];
		entrada.readFully(protocolo);
		if (!UtilBitTorrent.igualesConstante(PROTOCOLO, protocolo)) {
			throw new IOException();
		}
		byte[] reservados = new byte[8];
		byte[] infoHash = new byte[20];
		byte[] peerId = new byte[20];
		entrada.readFully(reservados);
		entrada.readFully(infoHash);
		entrada.readFully(peerId);
		return new HandshakeBitTorrent(reservados, infoHash, peerId);
	}

	static void escribir(DataOutputStream salida, byte[] infoHash, byte[] peerId, boolean extensiones)
			throws IOException {
		salida.writeByte(PROTOCOLO.length);
		salida.write(PROTOCOLO);
		byte[] reservados = new byte[8];
		if (extensiones) {
			reservados[5] |= 0x10;
		}
		salida.write(reservados);
		salida.write(infoHash);
		salida.write(peerId);
		salida.flush();
	}

	byte[] infoHash() {
		return infoHash.clone();
	}

	byte[] peerId() {
		return peerId.clone();
	}

	boolean soportaExtensiones() {
		return (reservados[5] & 0x10) != 0;
	}
}
