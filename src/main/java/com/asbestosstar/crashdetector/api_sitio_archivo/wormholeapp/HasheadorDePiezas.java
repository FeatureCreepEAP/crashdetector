package com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp;

import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Calcula hashes SHA-1 de piezas sin mantener todo el archivo cifrado en
 * memoria.
 */
public final class HasheadorDePiezas {

	private final int tamanoPieza;
	private final ByteArrayOutputStream buffer;
	private final List<byte[]> hashes;

	public HasheadorDePiezas(int tamanoPieza) {
		if (tamanoPieza <= 0) {
			throw new IllegalArgumentException("El tamaño de pieza debe ser > 0");
		}

		this.tamanoPieza = tamanoPieza;
		this.buffer = new ByteArrayOutputStream();
		this.hashes = new ArrayList<>();
	}

	public void actualizar(byte[] data) {
		if (data == null || data.length == 0) {
			return;
		}

		buffer.write(data, 0, data.length);

		while (buffer.size() >= tamanoPieza) {
			byte[] acumulado = buffer.toByteArray();

			byte[] pieza = new byte[tamanoPieza];
			System.arraycopy(acumulado, 0, pieza, 0, tamanoPieza);

			byte[] resto = new byte[acumulado.length - tamanoPieza];
			System.arraycopy(acumulado, tamanoPieza, resto, 0, resto.length);

			buffer.reset();
			buffer.write(resto, 0, resto.length);

			hashes.add(sha1(pieza));
		}
	}

	public List<byte[]> finalizar() {
		byte[] resto = buffer.toByteArray();

		if (resto.length > 0) {
			hashes.add(sha1(resto));
		} else if (hashes.isEmpty()) {
			hashes.add(sha1(new byte[0]));
		}

		return new ArrayList<>(hashes);
	}

	private byte[] sha1(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(data);
			return md.digest();
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("No se pudo calcular SHA-1", e);
		}
	}
}