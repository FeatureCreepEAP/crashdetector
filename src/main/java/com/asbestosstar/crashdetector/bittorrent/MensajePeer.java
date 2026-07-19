package com.asbestosstar.crashdetector.bittorrent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/** Mensaje peer-wire con prefijo de longitud. */
final class MensajePeer {

	static final int KEEP_ALIVE = -1;
	static final int CHOKE = 0;
	static final int UNCHOKE = 1;
	static final int INTERESTED = 2;
	static final int NOT_INTERESTED = 3;
	static final int HAVE = 4;
	static final int BITFIELD = 5;
	static final int REQUEST = 6;
	static final int PIECE = 7;
	static final int CANCEL = 8;
	static final int EXTENDED = 20;
	static final int MAXIMO = 4 * 1024 * 1024 + 64 * 1024;

	final int id;
	final byte[] carga;

	MensajePeer(int id, byte[] carga) {
		this.id = id;
		this.carga = carga == null ? new byte[0] : carga;
	}

	static MensajePeer leer(DataInputStream entrada) throws IOException {
		int longitud = entrada.readInt();
		if (longitud == 0) {
			return new MensajePeer(KEEP_ALIVE, new byte[0]);
		}
		if (longitud < 1 || longitud > MAXIMO) {
			throw new IOException();
		}
		int id = entrada.readUnsignedByte();
		byte[] carga = new byte[longitud - 1];
		entrada.readFully(carga);
		return new MensajePeer(id, carga);
	}

	static synchronized void escribir(DataOutputStream salida, int id, byte[] carga) throws IOException {
		byte[] datos = carga == null ? new byte[0] : carga;
		salida.writeInt(1 + datos.length);
		salida.writeByte(id);
		salida.write(datos);
		salida.flush();
	}

	static synchronized void keepAlive(DataOutputStream salida) throws IOException {
		salida.writeInt(0);
		salida.flush();
	}
}
