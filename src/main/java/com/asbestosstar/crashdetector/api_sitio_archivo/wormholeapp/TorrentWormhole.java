package com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Construcción de torrents y bencode para wormhole.app.
 */
public final class TorrentWormhole {

	private static final byte[] ANNOUNCE_URL = "wss://wormhole.app/websocket".getBytes(StandardCharsets.UTF_8);
	private static final byte[] CREATED_BY = "WebTorrent/0108".getBytes(StandardCharsets.UTF_8);

	private TorrentWormhole() {
	}

	public static int calcularTamanoDePieza(int tamanoCifrado) {
		int tamanoBase;
		if (tamanoCifrado < 1024) {
			tamanoBase = 16384;
		} else {
			double kb = tamanoCifrado / 1024.0d;
			int exponente = (int) Math.floor(log2(kb) + 0.5d);
			tamanoBase = (1 << exponente);
			tamanoBase = Math.max(tamanoBase, 16384);
		}

		int tamanoMinimo = Math.min(tamanoCifrado, 5_000_000);
		tamanoMinimo = divisionHaciaArriba(tamanoMinimo, 16384) * 16384;

		return Math.max(tamanoBase, tamanoMinimo);
	}

	public static int redondearTamanoEnMb(int size) {
		double rawSizeMb = size / 1_000_000.0d;

		int factor = 1;
		int cantidad = (int) Math.round(rawSizeMb);

		while (cantidad > 100) {
			factor *= 10;
			cantidad = (int) Math.round(rawSizeMb / factor);
		}

		return cantidad * factor;
	}

	public static ResultadoConstruccionTorrent crearTorrentArchivoUnicoDesdeHashes(String nombreArchivo,
			int longitudCifrada, int tamanoPieza, List<byte[]> hashesDePiezas) throws GeneralSecurityException {

		byte[] piezas = aplanarHashes(hashesDePiezas);
		String nonceHex = CriptoWormhole.aHex(CriptoWormhole.bytesAleatorios(16));

		TreeMap<ClaveBytes, ValorBencode> info = new TreeMap<>();
		info.put(clave("length"), ValorBencode.entero(longitudCifrada));
		info.put(clave("name"), ValorBencode.bytes(nombreArchivo.getBytes(StandardCharsets.UTF_8)));
		info.put(clave("nonce"), ValorBencode.bytes(nonceHex.getBytes(StandardCharsets.UTF_8)));
		info.put(clave("piece length"), ValorBencode.entero(tamanoPieza));
		info.put(clave("pieces"), ValorBencode.bytes(piezas));
		info.put(clave("private"), ValorBencode.entero(1));

		byte[] infoBencoded = bencode(ValorBencode.dict(info));
		String infoHash = CriptoWormhole.sha1Hex(infoBencoded);

		TreeMap<ClaveBytes, ValorBencode> torrent = new TreeMap<>();
		torrent.put(clave("announce"), ValorBencode.bytes(ANNOUNCE_URL));
		torrent.put(clave("announce-list"),
				ValorBencode.lista(Arrays.asList(ValorBencode.lista(Arrays.asList(ValorBencode.bytes(ANNOUNCE_URL))))));
		torrent.put(clave("created by"), ValorBencode.bytes(CREATED_BY));
		torrent.put(clave("creation date"), ValorBencode.entero(Instant.now().getEpochSecond()));
		torrent.put(clave("info"), ValorBencode.dict(info));
		torrent.put(clave("private"), ValorBencode.entero(1));
		torrent.put(clave("url-list"), ValorBencode.lista(new ArrayList<>()));

		byte[] torrentBencoded = bencode(ValorBencode.dict(torrent));
		return new ResultadoConstruccionTorrent(torrentBencoded, infoHash);
	}

	public static ResultadoConstruccionTorrent crearTorrentMultiArchivoDesdeHashes(String nombreDirectorio,
			List<ArchivoMetadata> archivos, int tamanoPieza, List<byte[]> hashesDePiezas)
			throws GeneralSecurityException {

		byte[] piezas = aplanarHashes(hashesDePiezas);
		String nonceHex = CriptoWormhole.aHex(CriptoWormhole.bytesAleatorios(16));

		List<ValorBencode> listaArchivos = new ArrayList<>();
		for (ArchivoMetadata archivo : archivos) {
			TreeMap<ClaveBytes, ValorBencode> fileDict = new TreeMap<>();
			fileDict.put(clave("length"), ValorBencode.entero(archivo.longitudCifrada));

			List<ValorBencode> partes = new ArrayList<>();
			for (String parte : archivo.ruta.split("/")) {
				partes.add(ValorBencode.bytes(parte.getBytes(StandardCharsets.UTF_8)));
			}
			fileDict.put(clave("path"), ValorBencode.lista(partes));

			listaArchivos.add(ValorBencode.dict(fileDict));
		}

		TreeMap<ClaveBytes, ValorBencode> info = new TreeMap<>();
		info.put(clave("files"), ValorBencode.lista(listaArchivos));
		info.put(clave("name"), ValorBencode.bytes(nombreDirectorio.getBytes(StandardCharsets.UTF_8)));
		info.put(clave("nonce"), ValorBencode.bytes(nonceHex.getBytes(StandardCharsets.UTF_8)));
		info.put(clave("piece length"), ValorBencode.entero(tamanoPieza));
		info.put(clave("pieces"), ValorBencode.bytes(piezas));
		info.put(clave("private"), ValorBencode.entero(1));

		byte[] infoBencoded = bencode(ValorBencode.dict(info));
		String infoHash = CriptoWormhole.sha1Hex(infoBencoded);

		TreeMap<ClaveBytes, ValorBencode> torrent = new TreeMap<>();
		torrent.put(clave("announce"), ValorBencode.bytes(ANNOUNCE_URL));
		torrent.put(clave("announce-list"),
				ValorBencode.lista(Arrays.asList(ValorBencode.lista(Arrays.asList(ValorBencode.bytes(ANNOUNCE_URL))))));
		torrent.put(clave("created by"), ValorBencode.bytes(CREATED_BY));
		torrent.put(clave("creation date"), ValorBencode.entero(Instant.now().getEpochSecond()));
		torrent.put(clave("info"), ValorBencode.dict(info));
		torrent.put(clave("private"), ValorBencode.entero(1));
		torrent.put(clave("url-list"), ValorBencode.lista(new ArrayList<>()));

		byte[] torrentBencoded = bencode(ValorBencode.dict(torrent));
		return new ResultadoConstruccionTorrent(torrentBencoded, infoHash);
	}

	public static byte[] bencode(ValorBencode valor) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		escribirBencode(out, valor);
		return out.toByteArray();
	}

	private static void escribirBencode(ByteArrayOutputStream out, ValorBencode valor) {
		switch (valor.tipo) {
		case ENTERO:
			out.write('i');
			escribirAscii(out, Long.toString(valor.entero));
			out.write('e');
			break;

		case BYTES:
			escribirAscii(out, Integer.toString(valor.bytes.length));
			out.write(':');
			out.write(valor.bytes, 0, valor.bytes.length);
			break;

		case LISTA:
			out.write('l');
			for (ValorBencode item : valor.lista) {
				escribirBencode(out, item);
			}
			out.write('e');
			break;

		case DICCIONARIO:
			out.write('d');
			for (ClaveBytes key : valor.dict.keySet()) {
				byte[] keyBytes = key.bytes;
				escribirAscii(out, Integer.toString(keyBytes.length));
				out.write(':');
				out.write(keyBytes, 0, keyBytes.length);
				escribirBencode(out, valor.dict.get(key));
			}
			out.write('e');
			break;

		default:
			throw new IllegalStateException("Tipo bencode no soportado");
		}
	}

	private static void escribirAscii(ByteArrayOutputStream out, String s) {
		byte[] bytes = s.getBytes(StandardCharsets.US_ASCII);
		out.write(bytes, 0, bytes.length);
	}

	private static byte[] aplanarHashes(List<byte[]> hashes) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (byte[] hash : hashes) {
			out.write(hash, 0, hash.length);
		}
		return out.toByteArray();
	}

	private static int divisionHaciaArriba(int a, int b) {
		return (a + b - 1) / b;
	}

	private static double log2(double v) {
		return Math.log(v) / Math.log(2.0d);
	}

	private static ClaveBytes clave(String texto) {
		return new ClaveBytes(texto.getBytes(StandardCharsets.UTF_8));
	}

	public static final class ArchivoMetadata {
		public final String ruta;
		public final int longitudCifrada;

		public ArchivoMetadata(String ruta, int longitudCifrada) {
			this.ruta = ruta;
			this.longitudCifrada = longitudCifrada;
		}
	}

	public static final class ResultadoConstruccionTorrent {
		private final byte[] bytesTorrent;
		private final String infoHash;

		public ResultadoConstruccionTorrent(byte[] bytesTorrent, String infoHash) {
			this.bytesTorrent = bytesTorrent;
			this.infoHash = infoHash;
		}

		public byte[] bytesTorrent() {
			return bytesTorrent;
		}

		public String infoHash() {
			return infoHash;
		}
	}

	private static final class ClaveBytes implements Comparable<ClaveBytes> {
		private final byte[] bytes;

		private ClaveBytes(byte[] bytes) {
			this.bytes = bytes;
		}

		@Override
		public int compareTo(ClaveBytes o) {
			int min = Math.min(this.bytes.length, o.bytes.length);
			for (int i = 0; i < min; i++) {
				int a = this.bytes[i] & 0xFF;
				int b = o.bytes[i] & 0xFF;
				if (a != b) {
					return Integer.compare(a, b);
				}
			}
			return Integer.compare(this.bytes.length, o.bytes.length);
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ClaveBytes)) {
				return false;
			}
			return Arrays.equals(bytes, ((ClaveBytes) obj).bytes);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(bytes);
		}
	}

	public static final class ValorBencode {

		private enum Tipo {
			ENTERO, BYTES, LISTA, DICCIONARIO
		}

		private final Tipo tipo;
		private final long entero;
		private final byte[] bytes;
		private final List<ValorBencode> lista;
		private final TreeMap<ClaveBytes, ValorBencode> dict;

		private ValorBencode(Tipo tipo, long entero, byte[] bytes, List<ValorBencode> lista,
				TreeMap<ClaveBytes, ValorBencode> dict) {
			this.tipo = tipo;
			this.entero = entero;
			this.bytes = bytes;
			this.lista = lista;
			this.dict = dict;
		}

		public static ValorBencode entero(long valor) {
			return new ValorBencode(Tipo.ENTERO, valor, null, null, null);
		}

		public static ValorBencode bytes(byte[] valor) {
			return new ValorBencode(Tipo.BYTES, 0, valor, null, null);
		}

		public static ValorBencode lista(List<ValorBencode> valor) {
			return new ValorBencode(Tipo.LISTA, 0, null, valor, null);
		}

		public static ValorBencode dict(TreeMap<ClaveBytes, ValorBencode> valor) {
			return new ValorBencode(Tipo.DICCIONARIO, 0, null, null, valor);
		}
	}
}