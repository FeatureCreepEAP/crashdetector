package com.asbestosstar.crashdetector.bittorrent;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;

/**
 * Crea metainfo BitTorrent v1 compatible con archivos y carpetas.
 *
 * Las piezas se calculan como un flujo continuo sobre todos los archivos,
 * siguiendo el orden lexicográfico de sus rutas relativas.
 */
public final class CreadorTorrentV1 {

	private static final int PIEZA_MINIMA = 256 * 1024;
	private static final int PIEZA_MAXIMA = 4 * 1024 * 1024;
	private static final int OBJETIVO_PIEZAS = 1024;

	private CreadorTorrentV1() {
	}

	public interface Progreso {
		void actualizar(long procesados, long total);
	}

	public static Resultado crear(Path origen, List<String> trackers, boolean privado, Progreso progreso)
			throws Exception {
		if (origen == null || !Files.exists(origen)) {
			throw new IOException(MonitorDePID.idioma.bittorrentOrigenNoValido());
		}

		Path absoluto = origen.toAbsolutePath().normalize();
		List<EntradaArchivo> archivos = obtenerArchivos(absoluto);
		if (archivos.isEmpty()) {
			throw new IOException(MonitorDePID.idioma.bittorrentOrigenVacio());
		}

		long total = 0L;
		for (EntradaArchivo archivo : archivos) {
			total += archivo.longitud;
		}
		if (total <= 0L) {
			throw new IOException(MonitorDePID.idioma.bittorrentOrigenVacio());
		}

		int tamanoPieza = elegirTamanoPieza(total);
		byte[] piezas = calcularPiezas(archivos, tamanoPieza, total, progreso);
		String nombreRaiz = absoluto.getFileName() == null ? MonitorDePID.idioma.bittorrentNombreTorrentPorDefecto()
				: absoluto.getFileName().toString();

		Map<String, Object> info = new LinkedHashMap<String, Object>();
		info.put("name", nombreRaiz);
		info.put("piece length", Long.valueOf(tamanoPieza));
		info.put("pieces", piezas);
		if (privado) {
			info.put("private", Long.valueOf(1L));
		}

		if (Files.isRegularFile(absoluto)) {
			info.put("length", Long.valueOf(total));
		} else {
			List<Object> listaArchivos = new ArrayList<Object>();
			for (EntradaArchivo archivo : archivos) {
				Map<String, Object> item = new LinkedHashMap<String, Object>();
				item.put("length", Long.valueOf(archivo.longitud));
				List<Object> partes = new ArrayList<Object>();
				for (Path parte : archivo.relativa) {
					partes.add(parte.toString());
				}
				item.put("path", partes);
				listaArchivos.add(item);
			}
			info.put("files", listaArchivos);
		}

		Map<String, Object> raiz = new LinkedHashMap<String, Object>();
		if (trackers != null && !trackers.isEmpty()) {
			raiz.put("announce", trackers.get(0));
			List<Object> niveles = new ArrayList<Object>();
			for (String tracker : trackers) {
				List<Object> nivel = new ArrayList<Object>();
				nivel.add(tracker);
				niveles.add(nivel);
			}
			raiz.put("announce-list", niveles);
		}
		raiz.put("comment", MonitorDePID.idioma.bittorrentComentarioTorrent());
		raiz.put("created by", Statics.nombre_cd.obtener());
		raiz.put("creation date", Long.valueOf(System.currentTimeMillis() / 1000L));
		raiz.put("info", info);

		byte[] bytes = CodificadorBencode.codificar(raiz);
		String magnet = MetainfoTorrent.desdeBytes(bytes).crearMagnet(trackers);
		return new Resultado(bytes, magnet, total, tamanoPieza, nombreRaiz);
	}

	private static List<EntradaArchivo> obtenerArchivos(final Path origen) throws IOException {
		final List<EntradaArchivo> archivos = new ArrayList<EntradaArchivo>();

		if (Files.isRegularFile(origen)) {
			archivos.add(new EntradaArchivo(origen, origen.getFileName(), Files.size(origen)));
			return archivos;
		}
		if (!Files.isDirectory(origen)) {
			return archivos;
		}

		Files.walkFileTree(origen, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path archivo, BasicFileAttributes atributos) throws IOException {
				if (atributos.isRegularFile() && !Files.isSymbolicLink(archivo)) {
					Path relativa = origen.relativize(archivo);
					archivos.add(new EntradaArchivo(archivo, relativa, atributos.size()));
				}
				return FileVisitResult.CONTINUE;
			}
		});

		Collections.sort(archivos, new Comparator<EntradaArchivo>() {
			@Override
			public int compare(EntradaArchivo a, EntradaArchivo b) {
				return a.relativa.toString().replace(File.separatorChar, '/')
						.compareTo(b.relativa.toString().replace(File.separatorChar, '/'));
			}
		});
		return archivos;
	}

	private static byte[] calcularPiezas(List<EntradaArchivo> archivos, int tamanoPieza, long total, Progreso progreso)
			throws Exception {
		MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
		ByteArrayOutputStream hashes = new ByteArrayOutputStream();
		byte[] buffer = new byte[128 * 1024];
		int bytesEnPieza = 0;
		long procesados = 0L;

		for (EntradaArchivo archivo : archivos) {
			try (InputStream entrada = new BufferedInputStream(Files.newInputStream(archivo.absoluta))) {
				int leidos;
				while ((leidos = entrada.read(buffer)) >= 0) {
					if (leidos == 0) {
						continue;
					}
					int offset = 0;
					while (offset < leidos) {
						int cantidad = Math.min(tamanoPieza - bytesEnPieza, leidos - offset);
						sha1.update(buffer, offset, cantidad);
						offset += cantidad;
						bytesEnPieza += cantidad;
						procesados += cantidad;

						if (bytesEnPieza == tamanoPieza) {
							hashes.write(sha1.digest());
							sha1.reset();
							bytesEnPieza = 0;
						}
					}
					if (progreso != null) {
						progreso.actualizar(procesados, total);
					}
				}
			}
		}

		if (bytesEnPieza > 0) {
			hashes.write(sha1.digest());
		}
		return hashes.toByteArray();
	}

	private static int elegirTamanoPieza(long total) {
		int pieza = PIEZA_MINIMA;
		while (pieza < PIEZA_MAXIMA && divisionHaciaArriba(total, pieza) > OBJETIVO_PIEZAS) {
			pieza *= 2;
		}
		return pieza;
	}

	private static long divisionHaciaArriba(long a, long b) {
		return (a + b - 1L) / b;
	}

	private static final class EntradaArchivo {
		final Path absoluta;
		final Path relativa;
		final long longitud;

		EntradaArchivo(Path absoluta, Path relativa, long longitud) {
			this.absoluta = absoluta;
			this.relativa = relativa;
			this.longitud = longitud;
		}
	}

	public static final class Resultado {
		private final byte[] bytesTorrent;
		private final String magnet;
		private final long tamanoTotal;
		private final int tamanoPieza;
		private final String nombre;

		Resultado(byte[] bytesTorrent, String magnet, long tamanoTotal, int tamanoPieza, String nombre) {
			this.bytesTorrent = bytesTorrent;
			this.magnet = magnet;
			this.tamanoTotal = tamanoTotal;
			this.tamanoPieza = tamanoPieza;
			this.nombre = nombre;
		}

		public byte[] bytesTorrent() {
			return bytesTorrent.clone();
		}

		public String magnet() {
			return magnet;
		}

		public long tamanoTotal() {
			return tamanoTotal;
		}

		public int tamanoPieza() {
			return tamanoPieza;
		}

		public String nombre() {
			return nombre;
		}
	}
}
