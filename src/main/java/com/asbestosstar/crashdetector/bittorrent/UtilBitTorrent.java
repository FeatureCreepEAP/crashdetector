package com.asbestosstar.crashdetector.bittorrent;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Locale;

/** Utilidades binarias y de URI del protocolo BitTorrent. */
final class UtilBitTorrent {

	private static final SecureRandom ALEATORIO = new SecureRandom();
	private static final char[] BASE32 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();

	private UtilBitTorrent() {
	}

	static byte[] crearPeerId() {
		byte[] id = new byte[20];
		byte[] prefijo = "-CD0100-".getBytes(StandardCharsets.US_ASCII);
		System.arraycopy(prefijo, 0, id, 0, prefijo.length);
		for (int i = prefijo.length; i < id.length; i++) {
			id[i] = (byte) ('0' + ALEATORIO.nextInt(10));
		}
		return id;
	}

	static int enteroAleatorio() {
		return ALEATORIO.nextInt();
	}

	static String hex(byte[] datos) {
		StringBuilder texto = new StringBuilder(datos.length * 2);
		for (byte dato : datos) {
			texto.append(String.format(Locale.ROOT, "%02x", dato & 0xff));
		}
		return texto.toString();
	}

	static byte[] desdeHex(String texto) {
		String valor = texto == null ? "" : texto.trim();
		if ((valor.length() & 1) != 0) {
			throw new IllegalArgumentException("Hexadecimal con longitud impar");
		}
		byte[] datos = new byte[valor.length() / 2];
		for (int i = 0; i < datos.length; i++) {
			int alto = Character.digit(valor.charAt(i * 2), 16);
			int bajo = Character.digit(valor.charAt(i * 2 + 1), 16);
			if (alto < 0 || bajo < 0) {
				throw new IllegalArgumentException("Hexadecimal no válido");
			}
			datos[i] = (byte) ((alto << 4) | bajo);
		}
		return datos;
	}

	static byte[] desdeBase32(String texto) {
		String valor = texto == null ? "" : texto.trim().replace("=", "").toUpperCase(Locale.ROOT);
		int buffer = 0;
		int bits = 0;
		java.io.ByteArrayOutputStream salida = new java.io.ByteArrayOutputStream();
		for (int i = 0; i < valor.length(); i++) {
			char c = valor.charAt(i);
			int indice = indiceBase32(c);
			if (indice < 0) {
				throw new IllegalArgumentException("Base32 no válido");
			}
			buffer = (buffer << 5) | indice;
			bits += 5;
			while (bits >= 8) {
				bits -= 8;
				salida.write((buffer >>> bits) & 0xff);
			}
		}
		return salida.toByteArray();
	}

	private static int indiceBase32(char c) {
		if (c >= 'A' && c <= 'Z') {
			return c - 'A';
		}
		if (c >= '2' && c <= '7') {
			return 26 + c - '2';
		}
		return -1;
	}

	static String codificarParametroBinario(byte[] datos) {
		StringBuilder texto = new StringBuilder(datos.length * 3);
		for (byte dato : datos) {
			int v = dato & 0xff;
			if ((v >= 'a' && v <= 'z') || (v >= 'A' && v <= 'Z') || (v >= '0' && v <= '9') || v == '-' || v == '_'
					|| v == '.' || v == '~') {
				texto.append((char) v);
			} else {
				texto.append('%');
				texto.append(Character.toUpperCase(Character.forDigit((v >>> 4) & 0xf, 16)));
				texto.append(Character.toUpperCase(Character.forDigit(v & 0xf, 16)));
			}
		}
		return texto.toString();
	}

	static String codificarParametroTexto(String texto) {
		try {
			return URLEncoder.encode(texto == null ? "" : texto, "UTF-8").replace("+", "%20");
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	static String decodificarParametroTexto(String texto) {
		try {
			return URLDecoder.decode(texto == null ? "" : texto, "UTF-8");
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	static void leerCompleto(InputStream entrada, byte[] destino) throws IOException {
		leerCompleto(entrada, destino, 0, destino.length);
	}

	static void leerCompleto(InputStream entrada, byte[] destino, int offset, int cantidad) throws IOException {
		int leidos = 0;
		while (leidos < cantidad) {
			int n = entrada.read(destino, offset + leidos, cantidad - leidos);
			if (n < 0) {
				throw new EOFException("Fin inesperado de la conexión BitTorrent");
			}
			leidos += n;
		}
	}

	static boolean igualesConstante(byte[] a, byte[] b) {
		if (a == null || b == null) {
			return false;
		}
		int diferencia = a.length ^ b.length;
		int maximo = Math.max(a.length, b.length);
		for (int i = 0; i < maximo; i++) {
			int aa = i < a.length ? a[i] & 0xff : 0;
			int bb = i < b.length ? b[i] & 0xff : 0;
			diferencia |= aa ^ bb;
		}
		return diferencia == 0;
	}
}
